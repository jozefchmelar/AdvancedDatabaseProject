package application.controller

import application.model.*
import javafx.beans.property.*
import javafx.collections.*
import model.*
import tornadofx.*

class CustomersController : Controller() {

    val companies = TableModel { Db.connection.nacitajZakaznikovFirmy("", "", 10, it).map(::CompanyModel) }
    val people = TableModel { Db.connection.nacitajZakaznikovOsoby("", "", 10, it).map(::PersonModel) }

    init {
        companies.current()
        people.current()
    }

    fun savePerson(item: Osoba) {

    }

    fun saveCompany(item: Firma) {

    }
}

class TableModel<T>(private val getCurrent: (Int) -> List<T>) {

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

    fun current() = getCurrent(page.value).let(list::setAll)

}
