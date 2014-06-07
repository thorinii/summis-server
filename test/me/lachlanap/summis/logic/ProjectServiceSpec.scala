package me.lachlanap.summis.logic

import me.lachlanap.summis.Builders._

import org.specs2.matcher._
import org.specs2.mock._
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

@RunWith(classOf[JUnitRunner])
class ProjectServiceSpec extends Specification with Mockito {
  "ProjectService" should {
    "create a project" in {
      val repo = mock[ProjectRepository]
      val sut = new ProjectService(repo)

      sut.create("A Project")

      there was one(repo).create(Project("a-project", "A Project"))
    }

    "retrieve a project" in {
      val repo = mock[ProjectRepository]
      val sut = new ProjectService(repo)

      repo.getBySlug("a-project") returns Some(Project("a-project", "A Project"))

      val result = sut.getBySlug("a-project")

      result must beSome
      result.get.name must be equalTo("A Project")
    }

    "get all projects with their latest versions" in {
      val repo = mock[ProjectRepository]
      val sut = new ProjectService(repo)

      val (project, releases, latest) = aProjectWithReleasesWithLatest
      repo.getAllProjectsWithReleases returns List((project, releases))

      val result = sut.getAllProjectsWithLatestRelease

      result must contain((project, Some(latest)))
    }
  }

  private def aProjectWithReleasesWithLatest = {
    val project = Project("a-project", "A Project")
    val latest = Release(project, "0.1.4")

    val releases = List(Release(project, "0.1.2"), latest, Release(project, "0.1.1"))

    (project, releases, latest)
  }
}
