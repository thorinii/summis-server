package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

import me.lachlanap.summis.logic._

class ConfiguredAccountRepository(config: Configuration) extends AccountRepository {
  val adminAccount = Account(config.getString("auth.admin.username").getOrElse("admin"),
                             config.getString("auth.admin.password").getOrElse("password"))

  def getAccountForName(username: String) = {
    if(username == adminAccount.username)
      Some(adminAccount)
    else
      None
  }
}

object Global {
  var _config: Config = _
  var _auth: Auth = _

  def config = _config
  def auth = _auth

  def init(app: Application) = {
    val version = app.configuration.getString("application.version").getOrElse("dev build")

    _config = Config(version)

    _auth = new Auth(new ConfiguredAccountRepository(app.configuration))
  }

  def contextFor(request: Request[_]) = {
    val currentPath = request.path
    val menu = buildMenu.map(item => if(item.url == currentPath) item.toActive else item)

    val loggedInAccount = request.session.get("user").flatMap { auth.getAccountForName(_) }

    Context(request,
            config.version,
            loggedInAccount,
            currentPath, Menu(menu),
            request.session)
  }

  private def buildMenu = {
    List(MenuItem("/", "Home"),
         MenuItem("/user/login", "Login"),
         MenuItem("/user/logout", "Logout"))
  }
}

case class Config(version: String)
