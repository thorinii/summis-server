package me.lachlanap.summis.logic

trait ProjectRepository {
  def create(project: Project): Unit

  def getBySlug(slug: String): Option[Project]
  def getAllProjectsWithReleases: Seq[(Project, List[Release])]
}

class ProjectService(repo: ProjectRepository) {
  def create(name: String) = {
    val project = Project(slugFor(name), name)
    repo.create(project)
  }

  private def slugFor(name: String) = {
    name.toLowerCase.replaceAll(" ", "-")
  }

  def getBySlug(slug: String) = repo.getBySlug(slug)

  def getAllProjectsWithLatestRelease = {
    repo.getAllProjectsWithReleases
        .map { case (project, releases) => (project, latestOf(releases)) }
  }

  private def latestOf(releases: List[Release]) = {
    releases.reduceOption(Release.Sorting.max)
  }
}
