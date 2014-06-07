package me.lachlanap.summis.database

import me.lachlanap.summis.logic._

import anorm._
import play.api._
import play.api.db.DB

class DBProjectRepository(app: Application) extends ProjectRepository {
  def create(project: Project) = {
  }

  def getBySlug(slug: String) = None

  def getAllProjectsWithReleases = List.empty

  import java.sql.Connection
  private def withDB[A](block: Connection => A) = DB.withConnection(block)(app)
}
