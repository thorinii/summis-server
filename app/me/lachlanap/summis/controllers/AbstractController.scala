package me.lachlanap.summis.controllers

import play.api._
import play.api.mvc._

trait AbstractController extends Controller {
  def ContextedAction(result: Context => Result) = Action { request =>
    val context = Global.contextFor(request)

    result(context)
  }

  def LoggedInAction(result: Context => Result) = ContextedAction { context =>
    if(context.loggedInAccount.isDefined)
      result(context)
    else
      Redirect(routes.User.loginPage())
  }
}
