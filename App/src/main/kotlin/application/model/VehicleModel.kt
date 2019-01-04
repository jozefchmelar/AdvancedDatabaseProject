package application.model

import model.*
import tornadofx.*

class VehicleModel : ItemViewModel<Vozidlo> {

    constructor(item: Vozidlo) : super(item)
    constructor() : super()

    val id = bind(Vozidlo::getId)
    val cennik = bind(Vozidlo::getCennik)
    val spz = bind(Vozidlo::getSpz)
    val znacka = bind(Vozidlo::getZnacka)
    val typ = bind(Vozidlo::getTyp)
    val fotkaCesta = bind(Vozidlo::getFotkaCesta)
//    val udrzby = bind(Vozidlo::getUdrzby)
    val datum_vyradenia = bind(Vozidlo::getDatum_vyradenia)

    override fun onCommit() {
        super.onCommit()
        item = Vozidlo(
                id.value,
                cennik.value,
                spz.value,
                znacka.value,
                typ.value,
                fotkaCesta.value,
                datum_vyradenia.value
        )
    }
}