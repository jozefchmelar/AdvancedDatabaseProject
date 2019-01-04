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
        Db.connection.pridajOsobu(item)
        people.current()

    }

    fun saveCompany(item: Firma) {
        Db.connection.pridajFirmu(item)
        companies.current()
    }
}

class TableModel<T>(private val getCurrent: (Int) -> List<T>) {

    val list = FXCollections.observableArrayList<T>()
    val page = SimpleIntegerProperty(1)

    fun more() {
        current(page.value + 1)
        page.set(page.value + 1)
    }

    fun less() {
        if (page.value == 1) return
        current(page.value - 1)
        page.set(page.value - 1)

    }

    fun current(apage:Int =1) = runAsync { getCurrent(apage).let(list::setAll) }

}
