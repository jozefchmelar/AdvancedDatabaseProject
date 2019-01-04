package application.view

import application.controller.*
import application.model.RentalModel
import javafx.geometry.*
import javafx.scene.layout.*
import tableviewpag
import tornadofx.*

class RentalView : View("Rental") {
    private val controller: RentalController by inject()

    override val root = borderpane {
        padding = Insets(20.0)

        center = tabpane {
            tab("Rentals") {
                tableviewpag(controller.rentals) {
                    column("id", RentalModel::id).apply { isSortable = false }
//                    column("vozidlo", RentalModel::vozidlo).apply { isSortable = false }
//                    column("zakaznik", RentalModel::zakaznik).apply { isSortable = false }
                    column("datumOD", RentalModel::datumOD).apply { isSortable = false }
                    column("datumDO", RentalModel::datumDO).apply { isSortable = false }
                    smartResize()
                }
            }
            tab("New rent") {

            }
        }
    }
}