package application.model

import model.Xml.Spolahlivost.*
import tornadofx.*
import java.time.*

class XmlVozidloModel : ItemViewModel<Vozidlo> {
    constructor(item: Vozidlo) : super(item)
    constructor() : super()

    val poradie = bind(Vozidlo::getPoradie)
    val koeficientSpolahlivosti = bind(Vozidlo::getKoeficientSpolahlivosti)
    val znacka = bind(Vozidlo::getZnacka)
    val typ = bind(Vozidlo::getTyp)
    val spz = bind(Vozidlo::getSpz)
    val dniPrevadzky = bind(Vozidlo::getDniPrevadzky)
    val dniOprav = bind(Vozidlo::getDniOprav)
    val zarobok = bind(Vozidlo::getZarobok)
}

class UsageModel : ItemViewModel<Pair<LocalDate,Int>>{
    constructor(item: Pair<LocalDate,Int>) : super(item)
    constructor() : super()

    val date  = bind(Pair<LocalDate,Int>::first)
    val usage = bind(Pair<LocalDate,Int>::second)
}