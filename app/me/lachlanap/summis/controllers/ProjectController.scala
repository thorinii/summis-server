package me.lachlanap.summis.controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc._

import me.lachlanap.summis.logic._

object ProjectController extends AbstractController {
  def info(projectName: String) = Main.index

  val createProjectForm = Form(
    mapping(
      "name" -> text
    )(CreateProjectFormData.apply)(CreateProjectFormData.unapply)
  )

  def createPage = LoggedInAction { context =>
    Ok(me.lachlanap.summis.controllers.html.createProject(context, createProjectForm))
  }

  def create = LoggedInAction { context =>
    createProjectForm.bindFromRequest()(context.request).fold(
      formWithErrors => {
        BadRequest(me.lachlanap.summis.controllers.html.createProject(context, formWithErrors))
      }, createProjectFormData => {
        val projectName = createProjectFormData.name

        Global.projects.create(projectName)
        Redirect(routes.ProjectController.info("test"))
      }
    )
  }
}

case class CreateProjectFormData(name: String)
