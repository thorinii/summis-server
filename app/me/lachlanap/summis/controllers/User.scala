package me.lachlanap.summis.controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc._

case class LoginFormData(username: String, password: String)

object User extends Controller {
  def validate(username: String, password: String) = {
    if(Global.auth.isValidLogin(username, password))
      Some(LoginFormData(username, password))
    else
      None
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
      Ok(me.lachlanap.summis.views.html.login(None, Global.config.version, loginForm))
    }
  }

  def login() = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(me.lachlanap.summis.views.html.login(None, Global.config.version, formWithErrors))
      }, loginFormData => {
        Redirect(routes.Main.index()).withSession("logged-in" -> loginFormData.username)
      }
    )
  }
}
