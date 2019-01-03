package application.model

import model.*
import tornadofx.*
import java.time.*
import java.util.*

class PricingModel : ItemViewModel<Cennik> {

    constructor(item:Cennik) : super(item)
    constructor() : super()

    val id        = bind(Cennik::getId)
    val cena_den  = bind { item?.cena_den?.toString()?.toProperty() ?: "0.0".toProperty()}
    val poplatok  = bind { item?.poplatok?.toString()?.toProperty() ?: "0.0".toProperty()}
    val platny_od = bind{ if(item==null) LocalDate.now().toProperty() else item.platny_od?.toLocalDate()?.toProperty()}
    val platny_do = bind{ if(item==null) LocalDate.now().toProperty() else item.platny_do?.toLocalDate()?.toProperty()}

    override fun onCommit() {
        super.onCommit()
        item = Cennik(
               id.value,
               cena_den.value.toDouble(),
               poplatok.value.toDouble(),
               platny_od.value.toDate(),
               platny_do.value.toDate()
        )
    }
}

fun Date?.toLocalDate(): LocalDate = this?.time?.let { LocalDate.ofEpochDay(it) } ?: LocalDate.now()
fun LocalDate.toDate() = Date(toEpochDay())