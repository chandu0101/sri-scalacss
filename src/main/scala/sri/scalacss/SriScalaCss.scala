package sri.scalacss

import org.scalajs.dom.raw.HTMLStyleElement
import org.scalajs.dom.document
import sri.core.ReactElement
import sri.web.vdom.htmltags._

import scala.scalajs.js
import scalacss.defaults.PlatformExports
import scalacss.internal._

/**
 * https://github.com/japgolly/scalacss/blob/master/ext-react/src/main/scala/scalacss/React.scala
 */
object SriScalaCssFns {

  def createStyleTag(styleStr: String): ReactElement = style(`type` = "text/css")(styleStr)

  class ReactElementRenderer(s: Renderer[String]) extends Renderer[ReactElement] {
    override def apply(css: Css) = createStyleTag(s(css))
  }

}

trait SriScalaCssImplicits extends PlatformExports{

  import SriScalaCssFns._

  implicit final def styleaToClassName(s: StyleA): js.UndefOr[String] =
    s.htmlClass

  implicit final def styleaToClassName2(s: StyleA): String =
    s.htmlClass

  implicit final def cssReactElementRenderer(implicit s: Renderer[String]): Renderer[ReactElement] =
    new ReactElementRenderer(s)

}

object Defaults extends SriScalaCssImplicits
