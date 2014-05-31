package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

object Main extends AbstractController {
  def index = ContextedAction { context =>
    Ok(me.lachlanap.summis.controllers.html.index(context))
  }
}
