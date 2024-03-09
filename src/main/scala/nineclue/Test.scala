package nineclue

import org.scalajs.dom
import org.scalajs.dom.{document, window}
import scalatags.JsDom.all.*
import org.scalajs.dom.Event
import scala.scalajs.js, js.annotation.JSGlobal
import org.scalajs.dom.Window
import scala.scalajs.js.Promise
import scala.scalajs.js.Thenable.Implicits.*

object Main:
    implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

    @js.native
    @JSGlobal("window.__TAURI__.core")
    object TauriCore extends js.Object:
        def invoke(fname: String, a: js.Object): Promise[js.Object] = js.native

    def setup2 = 
        val getter = input(id := "greet-input", placeholder := "Enter a name...").render
        val msg = p("").render
        frag(
            h1("Welcome to Tauri!"),
            div(cls := "row",
                Seq(("https://tauri.app", "/assets/tauri.svg", "logo tauri", "Tauri logo"), 
                    ("https://scala-js.org", "/assets/scala-js-logo.svg", "logo vanilla", "Scala.js logo")).map(
                        (loc, srcLoc, classes, alternative) =>
                            a(href := loc, target := "_blank", img(src := srcLoc, cls := classes, alt := alternative))
                    )
            ),
            p("Click on the Tauri logo to learn more about the framework"),
            form(cls := "row", id := "greet-form",
                onsubmit := { (e: Event) => 
                        e.preventDefault()
                        TauriCore.invoke("greet", js.Dynamic.literal("name" -> getter.value)).toFuture.onComplete(tjso =>
                            tjso.toOption.foreach(jso => msg.textContent = jso.asInstanceOf[String]))
                    },
                getter,
                button(`type` := "submit", "Greet")
            ),
            msg,
        ).render

    def setup = 
        div("Hello from Scala!").render

    def main(as: Array[String]): Unit = 
        val container = document.getElementById("container")
        container.appendChild(setup2)