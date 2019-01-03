package application.controller

import application.model.*
import javafx.beans.property.*
import javafx.collections.*
import model.*
import tornadofx.*

class CustomersController : Controller() {
    val companies = FXCollections.observableArrayList<CompanyModel>()
    val page = SimpleIntegerProperty(1)

    val people = FXCollections.observableArrayList<PersonModel>()

     fun get(page:Int =1) {
        companies.setAll(Db.connection.nacitajZakaznikovFirmy("","",10,page).map(::CompanyModel))
    }

    fun getMore() {
        page.set(page.value + 1)
        get(page.value)
    }

    fun getLess() {
        page.set(page.value -1)
        get(page.value)
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
