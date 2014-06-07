package me.lachlanap.summis.logic

case class Project(slug: String, name: String)

case class Release(project: Project, version: String)


object Release {
  val Sorting = Ordering.fromLessThan[Release] { case (a, b) =>
    a.version < b.version
  }
}
