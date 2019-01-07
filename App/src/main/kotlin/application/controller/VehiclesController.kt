package application.controller

import application.model.*
import javafx.scene.chart.*
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

    val pricing  = TableModel { Db.connection.nacitajCenniky("", "", 10, it).map(::PricingModel) }

    val vehicles = TableModel { Db.connection.nacitajVozidla(" ", "", 25, it).map(::VehicleModel) }
    val vehiclesUsage = TableModel { Db.connection.nacitajVozidla(" ", "vytazenost", 25, it).map(::VehicleModel) }
    val vehiclesFault = TableModel { Db.connection.nacitajVozidla(" ", "poruchovost", 25, it).map(::VehicleModel) }

    val vehiclesChart = Db.connection.poctyVozidiel().let {
        listOf(PieChart.Data("Funkcne ${it.funkcne}", it.funkcne.toDouble()),
                PieChart.Data("Vyradenie ${(it.vsetky - it.funkcne)}", (it.vsetky - it.funkcne).toDouble())
        ).observable()
    }


    init {
        update()
    }
    fun update(){
        vehicles.current()
        pricing.current()
        vehiclesUsage.current()
        vehiclesFault.current()
    }
}

