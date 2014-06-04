package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

import me.lachlanap.summis.logic._

object Main extends AbstractController {
  def index = ContextedAction { context =>
    val project = Project("summis", "Summis")

    val projects = List(
      (project, Some(Release(project, "1.0.2"))),
      (project, None)
    )
    Ok(me.lachlanap.summis.controllers.html.index(context, projects))
  }
}
