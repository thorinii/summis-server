package me.lachlanap.summis.logic

sealed trait Id
case class LongId(id: Long) extends Id
case object UnsetId extends Id


case class Project(id: Id, slug: String, name: String)

case class Release(project: Project, version: String)


object Release {
  val Sorting = Ordering.fromLessThan[Release] { case (a, b) =>
    a.version < b.version
  }
}
