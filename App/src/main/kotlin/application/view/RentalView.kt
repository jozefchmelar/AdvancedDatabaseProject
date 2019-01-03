package application.view

import application.controller.*
import javafx.geometry.*
import javafx.scene.layout.*
import tornadofx.*

class RentalView : View("Rental") {
    private val controller: MyController by inject()

    override val root = borderpane {
        padding = Insets(20.0)

        center = vbox {
            addClass("card")
            text("Rental").addClass("card-title")

            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
        }
    }
}