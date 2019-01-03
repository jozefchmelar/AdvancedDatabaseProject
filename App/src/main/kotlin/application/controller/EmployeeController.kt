package application.controller

import application.model.*
import javafx.collections.*

class CustomersController : MyController() {
    val companies = FXCollections.observableArrayList<CompanyModel>()
    val people = FXCollections.observableArrayList<PersonModel>()

    override fun get() {
        companies.setAll(Db.connection.nacitajZakaznikovFirmy("").map(::CompanyModel))
    }
}

