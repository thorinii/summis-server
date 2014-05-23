package me.lachlanap.summis.logic

class Auth(admin: Account) {
  def isValidLogin(username: String, password: String) = {
    admin.username == username && admin.password == password
  }
}

case class Account(username: String, password: String)
