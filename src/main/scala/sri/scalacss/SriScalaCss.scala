package sri.scalacss

import sri.core.{React, ReactElement}

import scala.scalajs.js
import scalacss.defaults.PlatformExports
import scalacss.internal._

/**
 * https://github.com/japgolly/scalacss/blob/master/ext-react/src/main/scala/scalacss/React.scala
 */
object SriScalaCssFns {

  def createStyleTag(styleStr: String): ReactElement = React.createElement("style",js.Dynamic.literal(`type` = "text/css"),styleStr)

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
