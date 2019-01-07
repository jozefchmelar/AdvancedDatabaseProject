package application.controller

import application.model.*
import javafx.beans.property.*
import javafx.scene.chart.*
import model.*
import tornadofx.*

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

    val vehicles = TableModel { Db.connection.nacitajVozidla(" ", "", 25, it).map(::VehicleModel) }
    val vehiclesFault = TableModel { Db.connection.nacitajVozidla(" ", "poruchovost", 25, it).map(::VehicleModel) }

    val spolahlivostXml = SimpleStringProperty()
    val spolahlivostTitle = SimpleStringProperty()

    val vynosyXml = SimpleStringProperty()
    val vynosyTitle = SimpleStringProperty()

    val spolahlivost = Db.connection.spolahlivostVozidiel(0.01).also { spolahlivostXml.set(it.xml);spolahlivostTitle.set(it.report.report.title) }.report.report.vozidlo.map(::XmlVozidloModel).observable()
    val vynosy = Db.connection.vynosyVozidiel(0.01).also { vynosyXml.set(it.xml);vynosyTitle.set(it.report.report.title) }.report.report.vozidlo.filter { it.zarobok != 0.0 }.map(::XmlVozidloModel).observable()

    val vehiclesChart = Db.connection.poctyVozidiel().let {
        listOf(PieChart.Data("Funkcne ${it.funkcne}", it.funkcne.toDouble()),
                PieChart.Data("Vyradenie ${(it.vsetky - it.funkcne)}", (it.vsetky - it.funkcne).toDouble())
        ).observable()
    }


    init {
        update()
    }

    fun update() {
        vehicles.current()
        pricing.current()
        vehiclesFault.current()
    }
}

