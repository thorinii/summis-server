package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

object Main extends Controller {
  def index() = Action { implicit request =>
    val version = Play.current.configuration.getString("application.version").getOrElse("dev build")

    Ok(me.lachlanap.summis.views.html.index(session.get("logged-in"), version))
  }
}
