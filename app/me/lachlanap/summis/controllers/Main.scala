package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

import me.lachlanap.summis.logic._

object Main extends AbstractController {
  def index = ContextedAction { context =>
    val projects = Global.projects.getAllProjectsWithLatestRelease

    Ok(me.lachlanap.summis.controllers.html.index(context, projects))
  }
}
