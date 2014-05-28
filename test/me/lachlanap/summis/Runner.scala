package me.lachlanap.summis

import org.specs2._
import org.specs2.matcher._
import org.junit.runner._
import org.openqa.selenium.htmlunit.HtmlUnitDriver

import play.api.test._
import play.api.test.Helpers._

class Runner(browser: TestBrowser, port: Int) extends MustThrownMatchers {
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

  def login: Unit = {
    login("admin", "password")
  }

  def login(username: String, password: String): Unit = {
    browser.$("#login").click()

    browser.$("#username").text(username)
    browser.$("#password").text(password)

    browser.$("#login-submit").click()
  }

  def logout: Unit = {
    browser.$("#logout").click()
  }
}

abstract class WithRunner extends WithBrowser {
  lazy val runner: Runner = new Runner(browser, port)
}
