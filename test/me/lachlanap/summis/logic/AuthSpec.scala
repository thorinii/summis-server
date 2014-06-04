package me.lachlanap.summis.logic

import org.specs2.matcher._
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class AuthSpec extends Specification {
  "Auth" should {
    "refuse a nonexistant user" in {
      val auth = new Auth(fixedRepository(None))

      auth.isValidLogin("a username", "a password") must beFalse
    }

    "refuse an invalid password" in {
      val auth = new Auth(fixedRepository(Some(Account("a username", "a password"))))

      auth.isValidLogin("a username", "the wrong password") must beFalse
    }

    "allow a matching login pair" in {
      val auth = new Auth(fixedRepository(Some(Account("a username", "a password"))))

      auth.isValidLogin("a username", "a password") must beTrue
    }
  }

  private def fixedRepository(account: Option[Account]) = new AccountRepository {
    def getAccountForName(username: String) = account
  }
}
