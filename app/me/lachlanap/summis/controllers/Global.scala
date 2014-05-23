package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

import me.lachlanap.summis.logic._

object Global {
  var _config: Config = _
  var _auth: Auth = _

  def config = _config
  def auth = _auth

  def init(app: Application) = {
    val version = app.configuration.getString("application.version").getOrElse("dev build")

    _config = Config(version)

    _auth = new Auth(Account(app.configuration.getString("auth.admin.username").getOrElse("admin"),
                             app.configuration.getString("auth.admin.password").getOrElse("password")))
  }
}

case class Config(
  version: String
)
