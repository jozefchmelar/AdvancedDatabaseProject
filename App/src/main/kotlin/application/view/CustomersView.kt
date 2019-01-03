package application.view

import application.controller.*
import application.model.*
import javafx.geometry.*
import javafx.scene.control.*
import javafx.scene.layout.*
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
