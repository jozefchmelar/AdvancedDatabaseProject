package application.model

import model.*
import tornadofx.*

class RentalModel : ItemViewModel<Vypozicka> {

    constructor(item: Vypozicka) : super(item)
    constructor() : super()

    val id       = bind(Vypozicka::getId)
    val vozidlo  = bind(Vypozicka::getVozidlo )
    val zakaznik = bind(Vypozicka::getZakaznik )
    val datumOD  = bind(Vypozicka::getDatumOD )
    val datumDO  = bind(Vypozicka::getDatumDO )

    override fun onCommit() {
        super.onCommit()
        item = Vypozicka(
                id.value,
                vozidlo.value,
                zakaznik.value,
                datumOD.value,
                datumDO.value
        )
    }
}