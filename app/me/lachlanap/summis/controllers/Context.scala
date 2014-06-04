package me.lachlanap.summis.controllers

import play.api.mvc._
import me.lachlanap.summis.logic._

case class Context(request: Request[_],
                   appVersion: String,
                   loggedInAccount: Option[Account],
                   url: String, menu: Menu,
                   session: Session) {

  /**
   * <code>username</code> is assumed to be an actual account.
   */
  def login(username: String) = {
    Context(request,
            appVersion,
            loggedInAccount,
            url, menu,
            session + ("user" -> username))
  }

  def logout = {
    Context(request,
            appVersion,
            loggedInAccount,
            url, menu,
            session - "user")
  }

  def url(project: Project) = {
    routes.ProjectController.info(project.slug)
  }

  def url(release: Release) = {
    "TODO"
  }
}

case class Menu(items: Seq[MenuItem])

case class MenuItem(url: String, name: String, active: Boolean = false) {
  def id = name.toLowerCase.replaceAll(" ", "-")
  def toActive = MenuItem(url, name, true)
}
