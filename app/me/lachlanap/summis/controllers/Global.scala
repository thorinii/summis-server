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
  var _config: Option[Config] = None
  var _auth: Option[Auth] = None
  var _projects: Option[ProjectService] = None

  def config = _config.get
  def auth = _auth.get
  def projects = _projects.get

  def init(app: Application) = {
    val version = app.configuration.getString("application.version").getOrElse("dev build")

    _config = Some(Config(version))

    _auth = Some(new Auth(new ConfiguredAccountRepository(app.configuration)))

    val projectRepository = new DBProjectRepository(app)
    _projects = Some(new ProjectService(projectRepository))
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

case class Config(version: String)
