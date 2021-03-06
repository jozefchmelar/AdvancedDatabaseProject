package application.controller

import application.model.*
import javafx.beans.property.*
import javafx.collections.*
import javafx.scene.chart.*
import model.*
import tornadofx.*
import java.text.*
import java.util.*
import kotlin.math.*

class VehiclesController : Controller() {

    val znacky = SimpleIntegerProperty(0)
    val autoPocty = SimpleIntegerProperty(0)
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
    val vytazenostZnaciek = Db.connection.vytazenostZnaciek().let {
        val suma = it.map { it.second }.sum()
        it.filter { it.second != .0 }
                .sortedBy { it.second }
                .take(round(it.count() * 0.2).toInt())
                .map {
                    PieChart.Data(it.first + " ${floor((it.second / suma) * 100)}%", (it.second / suma) * 100)
                }.observable()
    }

    init {
        update()
    }

    fun update() {
        vehicles.current()
        pricing.current()
        vehiclesFault.current()
    }

    fun getCounts(toDate: Date?, toDate1: Date?, value: VehicleModel?) {
        val sdf = SimpleDateFormat("yyyyMMdd")
        val from = sdf.format(toDate)
        val to = sdf.format(toDate1)
        Db.connection.poctyPreVozidla(from, to, value!!.id.value, value.znacka.value).let {
            znacky.set(it.first)
            autoPocty.set(it.second)
        }
    }

    val usages = FXCollections.observableArrayList<UsageModel>()

    fun getMostUsedDays(toDate: Date) {
        val sdf = SimpleDateFormat("yyyy")
        Db.connection.vytazeneDniVRoku(sdf.format(toDate)).map(::UsageModel).let {
            usages.setAll(it)
        }
    }
}

