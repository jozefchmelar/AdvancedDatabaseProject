package application.view

import application.controller.*
import javafx.geometry.*
import javafx.scene.layout.*
import tableviewpag
import tornadofx.*

class RentalView : View("Rental") {
    private val controller: RentalController by inject()

    override val root = borderpane {
        padding = Insets(20.0)

        center = tabpane{
            tab("Rentals"){
              //  tableviewpag(controller.rentals)
            }
            tab("New rent"){

            }
        }
    }
}