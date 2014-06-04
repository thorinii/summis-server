package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

import me.lachlanap.summis.logic._

object ProjectController extends AbstractController {
  def info(projectName: String) = ContextedAction { context =>
    NotFound
  }
}
