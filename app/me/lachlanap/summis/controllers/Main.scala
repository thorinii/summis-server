package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

object Main extends Controller {
  def index() = Action { implicit request =>
    Ok(me.lachlanap.summis.views.html.index(session.get("logged-in"), Global.config.version))
  }
}
