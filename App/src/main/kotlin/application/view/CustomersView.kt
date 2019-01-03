package application.view

import application.controller.*
import application.model.*
import javafx.geometry.*
import javafx.scene.layout.*
import tornadofx.*

class CustomersView : View("Customers") {
    private val controller: CustomersController by inject()


    override val root = borderpane {
      //  controller.getCustomers()
        padding = Insets(20.0)

        center = vbox {
            addClass("card")
            text("Customers").addClass("card-title")

            vgrow = Priority.ALWAYS
            hgrow = Priority.ALWAYS

            tableview(controller.companies) {
                vgrow = Priority.ALWAYS
                hgrow = Priority.ALWAYS
                smartResize()
                column("Nazov", CompanyModel::nazov).apply { isSortable = false }
                column("Ico", CompanyModel::ico).apply { isSortable = false }
                column("Kontakt", CompanyModel::kontakt).apply { isSortable = false }
            }
            button("Get"){action{ runAsync{ controller.get()}}}
        }
    }
}
