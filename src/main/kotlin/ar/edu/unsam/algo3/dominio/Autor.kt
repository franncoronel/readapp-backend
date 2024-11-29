package ar.edu.unsam.algo3.dominio

import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.repositorio.Entidad

class Autor(
    var nombre: String = "",
    var apellido: String = "",
    var seudonimo: String = "",
    var lenguaNativa: Lenguaje = Lenguaje.ESPANIOL,
    val edad: Int = 45,
    val premios: Int = 0
) : Entidad {
    var id = 0

    fun esConsagrado(): Boolean = esLongevo() && ganoPremio()

    fun esLongevo(): Boolean = edad >= 50

    fun ganoPremio(): Boolean = premios > 0

    fun coincideConUsuario(usuario: Usuario): Boolean = lenguaNativa == usuario.idioma

    //FUNCIONALIDAD REPOSITORIO
    override fun condicionBusqueda(busqueda: String): Boolean =
        nombre.contains(busqueda, ignoreCase=true) || apellido.contains(busqueda, ignoreCase=true) || seudonimo == busqueda

    override fun asignarID(identificador: Int) {
        id = identificador
    }

    override fun id(): Int = id

    override fun estaBienConstruido() {
        validarNombre()
        validarApellido()
        validarSeudonimo()
        validarLenguaNativa()
        validarEdad()
        validarPremios()
    }

    fun validarNombre() {
        if (nombre.isBlank()) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: NOMBRE")
    }

    fun validarApellido() {
        if (apellido.isBlank()) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: LENGUAJE")
    }

    fun validarSeudonimo() {
        if (seudonimo.isBlank()) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: SEUDÓNIMO")
    }

    fun validarLenguaNativa() {
        if (lenguaNativa == null) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: LENGUA NATIVA")
    }

    fun validarEdad() {
        if ((edad == null) || (edad < 0)) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: EDAD")
    }

    fun validarPremios() {
        if ((edad == null) || (edad < 0)) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: PREMIOS")
    }

}