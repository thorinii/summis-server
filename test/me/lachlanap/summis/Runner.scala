package me.lachlanap.summis

import org.specs2._
import org.specs2.matcher._
import org.junit.runner._
import org.openqa.selenium.htmlunit.HtmlUnitDriver

import play.api.test._
import play.api.test.Helpers._

class UIRunner(browser: TestBrowser, port: Int) extends MustThrownMatchers {
  import HtmlMatchers._

  // Disable javascript
  browser.getDriver().asInstanceOf[HtmlUnitDriver].setJavascriptEnabled(false)

  // Start out at the home page
  goto("/")

  def goto(url: String) = browser.goTo("http://localhost:" + port + url)

  def mustNotBeLoggedIn = {
    browser.pageSource.toLowerCase must contain("not logged in")
  }
  def mustBeLoggedIn = {
    browser.pageSource.toLowerCase must contain("logged in as admin")
  }

  def login(username: String = "admin", password: String = "password") = {
    browser.$("#login").click()

    browser.$("#username").text(username)
    browser.$("#password").text(password)

    browser.$("#login-submit").click()
  }

  def logout: Unit = {
    browser.$("#logout").click()
  }

  def createProject(name: String) = {
    browser.$("#create-project").click()

    browser.$("#name").text(name)

    browser.$("#submit").click()
  }

  def mustShowProjectOnHomePage(name: String) = {
    browser.$("#home").click()

    browser.$("#projects") must haveRow(s"with ($name, _)") {
      case List(projectName, _) if projectName == name => true
    }
  }
}

object Builders {
  val projectNames = List("Ubuntu", "Debian", "Windows", "Play", "Chrome", "Specs2")

  private def choose[T](from: Seq[T]) = projectNames((Math.random() * from.size).toInt)
  private def number(from: Int, to: Int) = (Math.random() * (to-from)).toInt + from

  def makeProjectName = choose(projectNames) + " " + number(1, 100)
}

abstract class WithRunner extends WithBrowser {
  import org.specs2.execute._

  lazy val ui = new UIRunner(browser, port)
  lazy val builders = Builders

  override def around[T: AsResult](t: => T): Result = {
    try {
      Helpers.running(TestServer(port, app))(AsResult.effectively(t))
    } catch {
      case e: Exception => throw e
    } finally {
      browser.quit()
    }
  }
}

object HtmlMatchers {
  import org.fluentlenium.core.domain._
  import scala.collection.JavaConversions._

  def haveRow(description: String)(matchingFunction: PartialFunction[List[String], Boolean]) = {
    new Matcher[FluentList[FluentWebElement]] {
      def apply[T <: FluentList[FluentWebElement]](l: Expectable[T]) = {
        val tableOpt = try {
          Some(l.value.first())
        } catch {
          case e: org.openqa.selenium.NoSuchElementException => None
        }

        tableOpt.filter(isTable(_)) match {
          case Some(table) =>
            val rows = tableToListOfLists(table)
            val success = rows.exists(row => matchingFunction.isDefinedAt(row) && matchingFunction(row))

            result(success,
                   "table contains matching row",
                   rows.map(_.mkString("(", ", ", ")")).mkString(",\n") + "\n does not have a row " + description,
                   l)
          case None =>
            result(false,
                   "table exists",
                   "table does not exist",
                   l)
        }
      }
    }
  }

  private def isTable(e: FluentWebElement) = e.getTagName == "table"

  private def tableToListOfLists(table: FluentWebElement) = {
    table.find("tr")
         .toList
         .map(trToList(_))
  }

  private def trToList(row: FluentWebElement) = {
    row.find("td")
       .toList
       .map(_.getText)
  }
}
