package application.view

import application.controller.*
import application.model.RentalModel
import javafx.geometry.*
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.util.*
import tableviewpag
import tornadofx.*

class RentalView : View("Rental") {
    private val controller: RentalController by inject()
    val selectedRental = RentalModel()
    override val root = borderpane {
        padding = Insets(20.0)

        center = tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            tab("Rentals") {
                tableviewpag(controller.rentals) {
                    column("id", RentalModel::id).apply { isSortable = false }
                    column("datumOD", RentalModel::datumOD).apply { isSortable = false }
                    column("datumDO", RentalModel::datumDO).apply { isSortable = false }
                    column("SPZ", RentalModel::vozidlo).apply {
                        isSortable = false
                        converter(Conv { it.spz })
                    }
                    column("Brand", RentalModel::vozidlo).apply {
                        isSortable = false
                        converter(Conv { it.znacka })
                    }
                    onSelectionChange {
                        val selected = it?.item
                        if(selected!=null){
                         //   controller.getDetails(selected)
                        }
                    }
                    smartResize()
                }
            }
           tab(RentalNewRentalView::class)
        }
    }
}

class Conv<T>(val p: (T) -> String) : StringConverter<T>() {
    override fun toString(`object`: T?): String {
        return `object`?.let { p(it) } ?: "N/A"
    }

    override fun fromString(string: String?): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}