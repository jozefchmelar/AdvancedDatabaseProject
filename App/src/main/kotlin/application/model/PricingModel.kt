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
    val platny_od = bind { item?.platny_od?.toLocalDate().toProperty()}//bind{ item.platny_do }//if(item==null) LocalDate.now().toProperty() else item.platny_od?.toLocalDate()?.toProperty()}
    val platny_do = bind { item?.platny_do?.toLocalDate().toProperty()}//bind{ item.platny_do.toInstant().let { LocalDateTime.ofInstant(it,ZoneId.systemDefault()) }.let { LocalDate.of(it.year,it.month,it.dayOfMonth) } }//if(item==null) LocalDate.now().toProperty() else item.platny_do?.toLocalDate()?.toProperty()}

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

fun Date.toLocalDate(): LocalDate = LocalDate.now() //this?.toInstant().let { LocalDateTime.ofInstant(it,ZoneId.systemDefault()) }.let { LocalDate.of(it.year,it.month,it.dayOfMonth) } ?: LocalDate.now()
fun LocalDate.toDate() = Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())