package application.controller

import application.model.*
import javafx.beans.property.*
import javafx.scene.chart.*
import model.*
import tornadofx.*
import java.text.*
import java.time.*
import java.util.*

class VehiclesState : Controller() {


    private fun getVehiclesWithoutProfit( page: Int = 1): List<Vozidlo> {
        val format = SimpleDateFormat("yyyyMMdd")
        val from = format.format(from.value.toDate())
        val to = format.format(to.value.toDate())
        println(from)
        println(to)
        val x = Db.connection.vozidlaBezZisku(from, to, 15, page)
        return x
    }



    val from = LocalDate.of(1989,1,1).toProperty()
    val to = LocalDate.now().plusMonths(2).toProperty()

    val vehiclesWithoutProfit = TableModel { getVehiclesWithoutProfit(it).map(::VehicleModel) }

}


