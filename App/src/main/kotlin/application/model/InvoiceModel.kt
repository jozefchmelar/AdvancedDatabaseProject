package application.model

import model.*
import tornadofx.*

class InvoiceModel : ItemViewModel<Faktura> {

    constructor(item: Faktura) : super(item)
    constructor() : super()

    val id = bind(Faktura::getId)
    val vypozicka = bind(Faktura::getVypozicka)
    val suma = bind(Faktura::getSuma)
    val datumVystavenia = bind(Faktura::getDatumVystavenia)
    val datumZaplatenia = bind(Faktura::getDatumZaplatenia)
    val car = bind { item.vypozicka.vozidlo.toProperty() }

    override fun onCommit() {
        super.onCommit()
        item = Faktura(
                id.value,
                vypozicka.value,
                suma.value,
                datumVystavenia.value,
                datumZaplatenia.value)
    }
}