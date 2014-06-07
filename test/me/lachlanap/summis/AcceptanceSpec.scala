package me.lachlanap.summis

import org.specs2.matcher._
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class AcceptanceSpec extends Specification {

  "the application" should {
    "show a home page" in new WithBrowser {
      import org.openqa.selenium.htmlunit.HtmlUnitDriver
      browser.getDriver().asInstanceOf[HtmlUnitDriver].setJavascriptEnabled(false)

      browser.goTo("http://localhost:" + port)

      browser.pageSource.toLowerCase must contain("not logged in")
    }
  }

  "the login page" should {
    "login an admin" in new WithRunner {
      ui.mustNotBeLoggedIn

      ui.login()

      ui.mustBeLoggedIn
    }

    "refuse anyone else" in new WithRunner {
      ui.mustNotBeLoggedIn

      ui.login("bad username", "bad password")

      ui.mustNotBeLoggedIn
    }
  }

  "the logout page" should {
    "logout an admin" in new WithRunner {
      ui.login()

      ui.logout

      ui.mustNotBeLoggedIn
    }
  }

  "creating a project" should {
    "show it on the home page" in new WithRunner {
      ui.login()

      val projectName = builders.makeProjectName
      ui.createProject(projectName)

      ui.mustShowProjectOnHomePage(projectName)
    }//.pendingUntilFixed
  }
}
