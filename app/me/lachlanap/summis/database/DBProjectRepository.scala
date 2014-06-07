package me.lachlanap.summis.database

import me.lachlanap.summis.logic._

import anorm._
import play.api._
import play.api.db.DB

class DBProjectRepository(app: Application) extends ProjectRepository {
  var projects = List.empty[Project]

  def create(project: Project) = {
    projects = project :: projects
  }

  def getBySlug(slug: String) = None

  def getAllProjectsWithReleases = projects.map { p => (p, List.empty[Release]) }


  import java.sql.Connection
  private def withDB[A](block: Connection => A) = DB.withConnection(block)(app)
}
