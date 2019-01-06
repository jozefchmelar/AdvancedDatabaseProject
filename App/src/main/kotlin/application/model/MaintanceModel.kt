package application.model

import model.*
import tornadofx.*

class MaintanceModel : ItemViewModel<Udrzba> {

    constructor(item: Udrzba) : super(item)
    constructor() : super()

    val pocetKM = bind { item?.pocetKM?.toString()?.toProperty() ?: "0.0".toProperty()}
    val cena = bind { item?.cena?.toString()?.toProperty() ?: "0.0".toProperty()}
    val datumOD = bind { item?.datumOD?.toLocalDate().toProperty()}
    val datumDO = bind { item?.datumDO?.toLocalDate().toProperty()}
    val popis   = bind(Udrzba::getPopis)

    override fun onCommit() {
        super.onCommit()
        item = Udrzba(
                pocetKM.value.toLong(),
                cena.value.toDouble(),
                datumOD.value.toDate(),
                datumDO.value.toDate(),
                popis.value
        )
    }
}