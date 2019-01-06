package application.controller

import application.model.*
import model.*
import tornadofx.*
import java.sql.*

class VehiclesController : Controller() {

    fun addMaintnace(value: Vozidlo, item: Udrzba?) {
        Db.connection.pridajUdrzbu(value, item)
        vehicles.current()
    }

    fun newVehicle(item: Vozidlo?) {
        Db.connection.pridajVozidlo(item)
        vehicles.current()
    }

    val pricing = TableModel { Db.connection.nacitajCenniky("", "", 10, it).map(::PricingModel) }

    val vehicles = TableModel {
        Db.connection.nacitajVozidla(" ", "", 25, it)
                .also {
                    println(it)
                }
                .map(::VehicleModel)
    }

    init {
        vehicles.current()
        pricing.current()
        println()
    }
}

