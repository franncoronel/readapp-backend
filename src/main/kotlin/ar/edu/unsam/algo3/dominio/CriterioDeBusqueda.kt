package ar.edu.unsam.algo3.dominio
import com.fasterxml.jackson.annotation.JsonProperty

interface CriterioDeBusqueda {
    @JsonProperty("minimo")
    fun minimo() = 0

    @JsonProperty("maximo")
    fun maximo() = 0

    fun nombre() : List<String>

    fun recomendacionAdecuada(recomendacion: Recomendacion, usuario: Usuario): Boolean = true
}

object Leedor : CriterioDeBusqueda {
    override fun nombre() = listOf("Leedor")
}

object Precavido : CriterioDeBusqueda {

    override fun nombre() = listOf("Precavido")

    override fun recomendacionAdecuada(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        usuario.loLeyoUnAmigo(recomendacion) || usuario.tieneUnLibroPendiente(recomendacion)
}

object Poliglota : CriterioDeBusqueda {

    override fun nombre() = listOf("Poliglota")

    override fun recomendacionAdecuada(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.cantidadDeIdiomas() >= 5
}

object Nativista : CriterioDeBusqueda {

    override fun nombre() = listOf("Nativista")

    override fun recomendacionAdecuada(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.librosRecomendados.all { it.autor.coincideConUsuario(usuario) }
}

class Calculador(val rango: ClosedRange<Int>) : CriterioDeBusqueda {
    @JsonProperty("minimo")
    override fun minimo() = this.rango.start

    @JsonProperty("maximo")
    override fun maximo() = this.rango.endInclusive

    override fun nombre() = listOf("Calculador")

    override fun recomendacionAdecuada(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.tiempoLecturaRecomendacionCompleto(usuario) in rango
}

object Demandante : CriterioDeBusqueda {

    override fun nombre() = listOf("Demandante")

    override fun recomendacionAdecuada(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.valoracionPromedio() in 4.0..5.0
}

object Experimentado : CriterioDeBusqueda {

    override fun nombre() = listOf("Experimentado")

    override fun recomendacionAdecuada(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        recomendacion.esConsagrada()
}

object Cambiante : CriterioDeBusqueda {

    override fun nombre() = listOf("Cambiante")

    override fun recomendacionAdecuada(recomendacion: Recomendacion, usuario: Usuario): Boolean =
        criterioActual(usuario).recomendacionAdecuada(recomendacion, usuario)

    fun criterioActual(usuario: Usuario): CriterioDeBusqueda =
        if (usuario.edad() > 25) {
            Calculador(10000..15000)
        } else
            Leedor
}

class MultiCriterio : CriterioDeBusqueda {
    val criterios: MutableSet<CriterioDeBusqueda> = mutableSetOf()

    override fun nombre() = criterios.flatMap{criterio -> criterio.nombre()}

    fun agregarCriterio(criterio: CriterioDeBusqueda) = criterios.add(criterio)

    fun quitarCriterio(criterio: CriterioDeBusqueda) = criterios.remove(criterio)

    override fun recomendacionAdecuada(recomendacion: Recomendacion, usuario: Usuario): Boolean {
        return criterios.any { criterio -> criterio.recomendacionAdecuada(recomendacion, usuario) }
    }
}