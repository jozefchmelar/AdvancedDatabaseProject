package application.controller

import application.model.*
import javafx.beans.property.*
import javafx.collections.*
import model.*
import tornadofx.*
import java.text.*
import java.util.*

class CustomersController : Controller() {

    val companies = TableModel { Db.connection.nacitajZakaznikovFirmy("", "", 25, it).map(::CompanyModel) }
    val companyRentDays = SimpleIntegerProperty(0)
    val people = TableModel { Db.connection.nacitajZakaznikovOsoby("", "", 25, it).map(::PersonModel) }
    val peopleRentDays = SimpleIntegerProperty(0)

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

    fun getCompanyRentDays(toDate: Date?, toDate1: Date?, value: CompanyModel) {
        val sdf = SimpleDateFormat("yyyyMMdd")
        val from = sdf.format(toDate)
        val to = sdf.format(toDate1)
        Db.connection.pocetAutZakaznik(from,to,value.ico.value).let(companyRentDays::set)
    }
    fun getPersonRentaDate(toDate: Date?, toDate1: Date?, value: PersonModel) {
        val sdf = SimpleDateFormat("yyyyMMdd")
        val from = sdf.format(toDate)
        val to = sdf.format(toDate1)
        Db.connection.pocetAutZakaznik(from,to,value.rodCislo.value).let(peopleRentDays::set)
    }

}

open class TableModel<T>(private val getCurrent: (Int) -> List<T>) {

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

    open fun current(apage: Int = 1) = runAsync { getCurrent(apage).let(list::setAll) }
}

