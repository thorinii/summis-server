package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

object Main extends Controller {
  def home() = Action {
    Ok("You are logged out.")
  }
}
