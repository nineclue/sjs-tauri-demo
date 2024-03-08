package nineclue

import org.scalajs.dom
import org.scalajs.dom.document
import scalatags.JsDom.all.*

object Main:
    def setup = 
        val container = document.getElementById("container")
        container.appendChild(
            div("Hello from Scala!").render
        )

    def main(as: Array[String]): Unit = 
        setup
        