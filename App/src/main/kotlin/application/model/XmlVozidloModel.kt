package application.model

import model.Xml.Spolahlivost.*
import tornadofx.*

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