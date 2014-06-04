package me.lachlanap.summis.logic

case class Project(slug: String, name: String)

case class Release(project: Project, version: String)
