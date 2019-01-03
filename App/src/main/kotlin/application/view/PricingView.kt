package application.view

import application.controller.*
import javafx.geometry.*
import javafx.scene.layout.*
import tornadofx.*

class PricingView : View("Pricing") {
    private val controller: MyController by inject()

    override val root = borderpane {
        padding = Insets(20.0)

        center = vbox {
            addClass("card")
            text("Pricing").addClass("card-title")

            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
        }
    }
}