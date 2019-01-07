package application.view.Customers

import application.controller.*
import application.model.*
import javafx.scene.control.*
import tornadofx.*

class CustomersView : View("Customers") {
    private val controller: CustomersController by inject()

    private val createdCustomer = PersonModel()
    private val createdCompany = CompanyModel()

    override val root = tabpane {
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        tab(CustomerPersonView::class)
        tab(CustomerCompanyView::class)
    }
}
