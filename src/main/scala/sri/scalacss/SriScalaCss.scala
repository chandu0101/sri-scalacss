package sri.scalacss

import org.scalajs.dom.raw.HTMLStyleElement
import org.scalajs.dom.document
import sri.core.ReactElement
import sri.web.vdom.htmltags._
import scala.scalajs.js
import scalacss.{Css, Env, Renderer, StyleA}
import scalacss.mutable.{StyleSheetRegistry, StyleSheet}

/**
 * https://github.com/japgolly/scalacss/blob/master/ext-react/src/main/scala/scalacss/React.scala
 */
object SriScalaCssFns {

  def createStyleTag(styleStr: String): ReactElement = style(`type` = "text/css")(styleStr)


  def createStyle(styleStr: String): HTMLStyleElement = {
    val e = document.createElement("style").asInstanceOf[HTMLStyleElement]
    e.`type` = "text/css"
    e appendChild document.createTextNode(styleStr)
    e
  }

  def installStyle(style: HTMLStyleElement): Unit =
    document.head appendChild style

  final class InlineSSReactExt(val ss: StyleSheet.Inline) extends AnyVal {

    /** Turns this StyleSheet into a `&lt;style&gt;` and adds it to the document DOM. */
    def addToDocument()(implicit s: Renderer[HTMLStyleElement], e: Env): Unit =
      installStyle(ss.render[HTMLStyleElement])
  }

  final class StyleSheetRegistryReactExt(val r: StyleSheetRegistry) extends AnyVal {

    /** Registered StyleSheets are turned into a `&lt;style&gt;` and added to the document DOM. */
    def addToDocumentOnRegistration()(implicit s: Renderer[String], e: Env): Unit = {
      r.onRegistrationN { ss =>
        val styleStr = ss.map(_.render[String]).mkString("\n")
        val style = createStyle(styleStr)
        installStyle(style)
      }
    }
  }

  class ReactElementRenderer(s: Renderer[String]) extends Renderer[ReactElement] {
    override def apply(css: Css) = createStyleTag(s(css))
  }

  class StyleElementRenderer(s: Renderer[String]) extends Renderer[HTMLStyleElement] {
    override def apply(css: Css) = createStyle(s(css))
  }

}

trait SriScalaCssImplicits {

  import SriScalaCssFns._

  implicit final def styleaToClassName(s: StyleA): js.UndefOr[String] =
    s.htmlClass

  implicit final def styleaToClassName2(s: StyleA): String =
    s.htmlClass

  implicit final def inlineSSReactExt(ss: StyleSheet.Inline) =
    new InlineSSReactExt(ss)

  implicit final def styleSheetRegistryReactExt(r: StyleSheetRegistry) =
    new StyleSheetRegistryReactExt(r)

  implicit final def cssReactElementRenderer(implicit s: Renderer[String]): Renderer[ReactElement] =
    new ReactElementRenderer(s)

  implicit final def cssStyleElementRenderer(implicit s: Renderer[String]): Renderer[HTMLStyleElement] =
    new StyleElementRenderer(s)
}

object Defaults extends SriScalaCssImplicits
