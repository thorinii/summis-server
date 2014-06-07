package me.lachlanap.summis.database

import me.lachlanap.summis.logic._

import anorm._
import anorm.SqlParser._
import play.api._

class DBProjectRepository(db: Database) extends ProjectRepository {
  def create(project: Project) = db.execute { implicit c =>
    SQL("INSERT INTO project(slug, name) VALUES ({slug}, {name})")
       .on('slug -> project.slug, 'name -> project.name)
       .executeInsert()
  }

  def getBySlug(slug: String) = None

  private val project = {
    get[Long]("id") ~
    get[String]("slug") ~
    get[String]("name") map {
      case id~slug~name => Project(LongId(id), slug, name)
    }
  }

  def getAllProjectsWithReleases = db.execute { implicit c =>
    SQL("SELECT id, slug, name FROM project").as(project.*)
                                             .map(p => (p, List.empty[Release]))
                                             .toList
  }
}
