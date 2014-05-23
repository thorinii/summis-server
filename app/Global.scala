import play.api._

object Global extends GlobalSettings {
  override def onStart(app: Application) = {
    me.lachlanap.summis.controllers.Global.init(app)
  }
}
