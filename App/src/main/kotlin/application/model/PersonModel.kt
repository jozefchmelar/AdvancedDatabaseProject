package application.model

import model.*
import tornadofx.*

class PersonModel : ItemViewModel<Osoba> {

    constructor(item: Osoba) : super(item)
    constructor() : super()

    val id = bind(Osoba::getId)
    val kontakt = bind(Osoba::getKontakt)
    val rodCislo = bind(Osoba::getRodCislo)
    val meno = bind(Osoba::getMeno)
    val priezvisko = bind(Osoba::getPrizvisko)
    override fun onCommit() {

        super.onCommit()
        item = Osoba(
                "0",
                kontakt.value,
                rodCislo.value,
                meno.value,
                priezvisko.value
        )
    }
}