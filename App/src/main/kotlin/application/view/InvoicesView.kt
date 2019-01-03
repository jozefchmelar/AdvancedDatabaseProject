package application.view

import application.controller.*
import javafx.geometry.*
import javafx.scene.layout.*
import tornadofx.*

class InvoicesView : View("Invoices") {
    private val controller: MyController by inject()

    override val root = borderpane {
        padding = Insets(20.0)

        center = vbox {
            addClass("card")
            text("Invoices").addClass("card-title")

            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS
        }
    }
}