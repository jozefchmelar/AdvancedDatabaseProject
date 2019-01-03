package application.controller

import application.model.*
import javafx.beans.property.*
import javafx.collections.*
import model.*

class CustomersController : MyController() {
    val companies = FXCollections.observableArrayList<CompanyModel>()
    val page = SimpleIntegerProperty(1)

    val people = FXCollections.observableArrayList<PersonModel>()

    override fun get() {
        companies.setAll(Db.connection.nacitajZakaznikovFirmy("").map(::CompanyModel))
    }

    fun getMore() {
        page.set(page.value + 1)
    }

    fun savePerson(item: Osoba) {

    }

    fun saveCompany(item: Firma) {

    }
}

class TableModel<T>(val getCurrent : (Int) -> List<*>) {

    val list = FXCollections.observableArrayList<T>()
    val page = SimpleIntegerProperty(1)

    fun more() {
        page.set(page.value + 1)
        current()
    }

    fun less() {
        if (page.value == 1) return
        page.set(page.value - 1)
        current()
    }

    fun current() = getCurrent(page.value)

}
