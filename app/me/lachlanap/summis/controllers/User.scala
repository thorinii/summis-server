package me.lachlanap.summis.controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc._

case class LoginFormData(username: String, password: String)

object User extends Controller {
  lazy val version = Play.current.configuration.getString("application.version").getOrElse("dev build")

  def validate(username: String, password: String) = {
    // TODO: check matches admin logins
    Some(LoginFormData(username, password))
  }

  val loginForm = Form(
    mapping(
      "username" -> text,
      "password" -> text
    )(LoginFormData.apply)(LoginFormData.unapply) verifying("Bad login pair", fields => fields match {
      case loginFormData => validate(loginFormData.username, loginFormData.password).isDefined
    })
  )

  def loginPage() = Action { implicit request =>
    session.get("logged-in").map { username =>
      Redirect(routes.Main.index())
    }.getOrElse {
      Ok(me.lachlanap.summis.views.html.login(None, version, loginForm))
    }
  }

  def login() = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(me.lachlanap.summis.views.html.login(None, version, formWithErrors))
      }, loginFormData => {
        Redirect(routes.Main.index()).withSession("logged-in" -> loginFormData.username)
      }
    )
  }
}
