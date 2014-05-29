package me.lachlanap.summis

import org.specs2.matcher._
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
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
      runner.mustNotBeLoggedIn

      runner.login

      runner.mustBeLoggedIn
    }

    "refuse anyone else" in new WithRunner {
      runner.mustNotBeLoggedIn

      runner.login("bad username", "bad password")

      runner.mustNotBeLoggedIn
    }
  }

  "the logout page" should {
    "logout an admin" in new WithRunner {
      runner.login

      runner.logout

      runner.mustNotBeLoggedIn
    }
  }
}
