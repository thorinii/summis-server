package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

import me.lachlanap.summis.database._
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
  private var _g: Option[Global] = None
  private def g = _g.get

  def config = g.config
  def auth = g.auth
  def projects = g.projects

  def init(app: Application) = {
    _g = Some(new Global(app))
  }

  def contextFor(request: Request[_]) = {
    val currentPath = request.path

    val loggedInAccount = request.session.get("user").flatMap { auth.getAccountForName(_) }

    val menu = instantiateMenu(siteMenu, currentPath, loggedInAccount.isDefined)

    Context(request,
            config.version,
            loggedInAccount,
            currentPath, menu,
            request.session)
  }

  private def instantiateMenu(menu: Menu, url: String, isLoggedIn: Boolean) = {
    Menu(menu.items
             .filterNot(item => item.url.contains("login") && isLoggedIn)
             .filterNot(item => !isLoggedIn && item.auth)
             .map(item => if(item.url == url) item.toActive else item))
  }

  private lazy val siteMenu = {
    Menu(List(MenuItem(routes.Main.index.url, "Home"),
              MenuItem(routes.ProjectController.create.url, "Create Project", auth=true),
              MenuItem(routes.User.login.url, "Login"),
              MenuItem(routes.User.logout.url, "Logout", auth=true)))
  }
}

class Global(app: Application) {
  private val version = app.configuration.getString("application.version").getOrElse("dev build")

  val config = Config(version)
  val auth = new Auth(new ConfiguredAccountRepository(app.configuration))
  val projects = new ProjectService(new DBProjectRepository(app))
}

case class Config(version: String)
