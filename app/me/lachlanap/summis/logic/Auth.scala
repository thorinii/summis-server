package me.lachlanap.summis.logic

case class Account(username: String, password: String)

trait AccountRepository {
  def getAccountForName(username: String): Option[Account]
}

trait AuthProvider {
  def isLoggedIn: Boolean
  def currentUser: Option[String]
}


class Auth(repository: AccountRepository) {
  def isValidLogin(username: String, password: String) = {
    repository.getAccountForName(username)
              .map(account => account.password == password)
              .getOrElse(false)
  }

  def getAccountForName(username: String) = repository.getAccountForName(username)
}
