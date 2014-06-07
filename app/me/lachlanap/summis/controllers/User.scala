package me.lachlanap.summis.controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc._

case class LoginFormData(username: String, password: String)

object User extends AbstractController {
  private def validate(username: String, password: String) = {
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

  def loginPage = ContextedAction { context =>
    context.loggedInAccount.map { _ =>
      Redirect(routes.Main.index())
    }.getOrElse {
      Ok(me.lachlanap.summis.controllers.html.login(context, loginForm))
    }
  }

  def login = ContextedAction { context =>
    loginForm.bindFromRequest()(context.request).fold(
      formWithErrors => {
        BadRequest(me.lachlanap.summis.controllers.html.login(context, formWithErrors))
      }, loginFormData => {
        Redirect(routes.Main.index()).withSession(context.login(loginFormData.username).session)
      }
    )
  }

  def logout = ContextedAction { context =>
    Redirect(routes.Main.index()).withSession(context.logout.session)
  }
}
