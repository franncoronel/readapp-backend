package ar.edu.unsam.algo3.dominio

import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.repositorio.Entidad
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

val LIMITE_PAGINAS: Int = 600
val LIMITE_VENTAS: Int = 10000
val LIMITE_EDICIONES: Int = 2
val LIMITE_TRADUCCIONES: Int = 5

class Libro(
    val palabras: Int = 40000,
    val paginas: Int = 1000,
    var ediciones: Int = 2,
    var ventasSemanales: Int = 10000,
    val lecturaCompleja: Boolean = false,

    @JsonIgnore
    val autor: Autor = Autor(),
    val lenguajeOriginal: Lenguaje = autor.lenguaNativa,
    var titulo: String = "",
    val imagen: String = "tapa-libro.jpg"
) : Entidad {
    var id = 0
    var traducciones = mutableSetOf<Lenguaje>()

    fun nombreAutor(): String = "${autor.nombre} ${autor.apellido}"

    fun convertirIdiomasAString() = idiomas().map{it.toJson()}

    fun idiomas(): MutableSet<Lenguaje> = (traducciones + lenguajeOriginal).toMutableSet()

    fun agregarTraduccion(traduccionesAAgregar: Set<Lenguaje>) {
        traducciones.addAll(traduccionesAAgregar)
    }

    fun borrarTraduccion(traduccionesABorrar: Set<Lenguaje>) {
        traducciones.removeAll(traduccionesABorrar)
    }

    fun esLargo(): Boolean = paginas > LIMITE_PAGINAS

    fun esDesafiante(): Boolean = esLargo() || lecturaCompleja

    fun esBestSeller(): Boolean = (superoVentas() && (superoEdiciones() || muchasTraducciones()))

    fun superoVentas(): Boolean = ventasSemanales >= LIMITE_VENTAS

    fun superoEdiciones(): Boolean = ediciones > LIMITE_EDICIONES

    fun muchasTraducciones(): Boolean = traducciones.size >= LIMITE_TRADUCCIONES

    //FUNCIONALIDAD REPOSITORIO

    override fun condicionBusqueda(busqueda: String): Boolean =
        titulo.contains(busqueda, ignoreCase = true) || autor.apellido.contains(busqueda, ignoreCase = true)

    override fun asignarID(identificador: Int) {
        id = identificador
    }

    override fun id(): Int = id

    override fun estaBienConstruido() {
        validaPalabras()
        validaEdiciones()
        validaLecturaCompleja()
        validaAutor()
        autor.estaBienConstruido()
        validaVentasSemanales()
        validaTitulo()
        validaLenguajeOriginal()
    }

    fun validaPalabras() {
        if (palabras <= 0) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: PALABRAS")
    }

    fun validaEdiciones() {
        if (ediciones <= 0) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: EDICIONES")
    }

    fun validaVentasSemanales() {
        if (ventasSemanales <= 0) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: VENTAS SEMANALES")
    }

    fun validaLecturaCompleja() {
        if (lecturaCompleja != null) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: LECTURA COMPLEJA")
    }

    fun validaAutor() {
        if (autor != null) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: AUTOR")
    }

    fun validaLenguajeOriginal() {
        if (lenguajeOriginal == null) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: LENGUAJE")
    }

    fun validaTitulo() {
        if (titulo.isBlank()) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: TITULO")
    }
}