package me.lachlanap.summis.database

import anorm._
import java.sql.Connection
import play.api._
import play.api.db.DB

class Database(app: Application) {
  def execute[A](block: Connection => A) = DB.withConnection(block)(app)
}
