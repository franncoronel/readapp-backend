package ar.edu.unsam.algo3.dominio

import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.repositorio.Entidad
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@JsonPropertyOrder("id","nombre", "apellido", "username", "fotoPerfil", "email", "fechaNacimiento", "tipoLector", "criterio")
class Usuario(
    var nombre: String = "",
    var apellido: String = "",
    var username: String = "",
    var password: String = "12345",
    var fotoPerfil:String = "perfil-default.png",
    var email: String = "",
    @JsonIgnore
    val rol: Rol = Rol.USUARIO,
    @JsonIgnore
    var fechaNacimiento: LocalDate = LocalDate.now().minusYears(20),
    var palabrasPorMinuto: Int = 100,

    @JsonIgnore
    var criterioDeBusqueda: CriterioDeBusqueda = Leedor,

    @JsonIgnore
    var tipoLector: Lector = Promedio,

    @JsonIgnore
    var idioma: Lenguaje = Lenguaje.ESPANIOL
) : Entidad {
    companion object {
        private const val FORMATO_FECHA = "dd/MM/yyyy"
        private val formateador = DateTimeFormatter.ofPattern(FORMATO_FECHA)
    }

    var id = 0
    var recomendacionesAValorar = mutableSetOf<Recomendacion>()

    var librosALeer = mutableSetOf<Libro>()

    var librosLeidos = mutableListOf<Libro>()

    @JsonIgnore
    var autoresPreferidos = mutableSetOf<Autor>()

    @JsonIgnore
    var amigos = mutableListOf<Usuario>()

    @JsonIgnore
    var recomendaciones = mutableSetOf<Recomendacion>() //Ver si queda, creo que no

    @JsonProperty("fechaNacimiento")
    fun darFormatoAFecha(): String = formateador.format(this.fechaNacimiento)

    // METODO AGREGADO EN ALGO 3

    @JsonProperty("criterioDeBusqueda")
    fun criterio() =
        criterioDeBusqueda.nombre()

    @JsonProperty("tipoLector")
    fun lector() =
        tipoLector.nombre()

    @JsonProperty("amigos")
    fun datosAmigos() =
        amigos.map { mutableListOf(it.nombre, it.apellido, it.username, it.fotoPerfil) } //falta agregar la foto de perfil

    fun agregarRecomendacion (recomendacion: Recomendacion) =
        recomendaciones.add(recomendacion)

// METODOS GENERALES DEL USUARIO

    fun edad(): Int = ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now()).toInt()

    fun tiempoDeLectura(libro: Libro): Int =
        (tiempoDeLecturaPromedio(libro) * tipoLector.calculoPorLector(libro, this)).toInt()

    fun tiempoDeLecturaPromedio(libro: Libro): Int = calculaTiempoDeLecturaPromedio(libro) * factorDesafiante(libro)

    fun calculaTiempoDeLecturaPromedio(libro: Libro): Int = libro.palabras / palabrasPorMinuto

    fun factorDesafiante(libro: Libro): Int = if (libro.esDesafiante()) 2 else 1

    fun agregarAmigo(usuario: Usuario) {
        if(esAmigo(usuario)){
            throw BusinessException("Este usuario ya pertenece a tu lista de amigos.")
        }
        amigos.add(usuario)
    }

    fun eliminarAmigo(usuario: Usuario) = amigos.remove(usuario)

    fun esAmigo(usuario: Usuario): Boolean = amigos.contains(usuario)

    fun esAutorPreferido(autor: Autor): Boolean = autoresPreferidos.contains(autor)

    fun hayLibrosLeidos(): Boolean = librosLeidos.isNotEmpty()

    fun leyoElLibro(libro: Libro): Boolean = librosLeidos.contains(libro)

    fun agregarLibroLeido(libro: Libro) {
        if(librosALeer.contains(libro))
            librosALeer.remove(libro)
        librosLeidos.add(libro)
    }

    fun eliminarLibroLeido(libro: Libro) = librosLeidos.remove(libro)

    fun eliminarLibroALeer(libro: Libro) = librosALeer.remove(libro)

    fun nombresLibrosLeidos(): MutableSet<Libro> = librosLeidos.toMutableSet()

    fun vecesLeido(libro: Libro) = librosLeidos.count {  it == libro }

    fun agregarLibroALeer(libro: Libro) {
        if (leyoElLibro(libro))
            throw BusinessException("No se puede agregar un libro leído a la lista de libros a leer")
        librosALeer.add(libro)
    }

    fun agregarAutorPreferido(autor: Autor) = autoresPreferidos.add(autor)

// METODOS USUARIO<->RECOMENDACION

    fun buscarRecomendacion(recomendacion: Recomendacion): Boolean =
        criterioDeBusqueda.recomendacionAdecuada(recomendacion, this)

    fun crearOEditarValoracion(recomendacion: Recomendacion, valor: Int, comentario: String) {
        recomendacion.agregarOEditarValoracion(this, valor, comentario, LocalDate.now())
    }

    fun agregarRecomendacionAValorar(recomendacion: Recomendacion) {
        if (!recomendacion.validarValoracion(this))
            throw BusinessException("No cumple las condiciones para agregar una valoracion")
        recomendacionesAValorar.add(recomendacion)
    }

// METODOS USUARIO<->PERFIL_USUARIO

    fun cambiaCriterio(nuevoCriterio: CriterioDeBusqueda) {
        criterioDeBusqueda = nuevoCriterio
    }

    fun librosLeidosAmigos(): MutableSet<Libro> {
        val librosLeidosPorAmigos = mutableSetOf<Libro>()
        amigos.forEach { amigo -> librosLeidosPorAmigos.addAll(amigo.librosLeidos) }
        return librosLeidosPorAmigos
    }

    fun loLeyoUnAmigo(recomendacion: Recomendacion): Boolean =
        librosLeidosAmigos().any { recomendacion.contieneLibro(it) }

    fun tieneUnLibroPendiente(recomendacion: Recomendacion): Boolean =
        librosALeer.any { recomendacion.contieneLibro(it) }
// METODOS USUARIO <-> TIPO_LECTOR

    fun cambiaTipoLector(nuevoTipoLector: Lector) {
        tipoLector = nuevoTipoLector
    }
// METODOS USUARIO <-> Centro de lectura

    fun solicitarReserva(centroDeLectura: CentroDeLectura) {
        centroDeLectura.registrarReserva(this)
    }

//FUNCIONALIDAD REPOSITORIO

    override fun condicionBusqueda(busqueda: String): Boolean =
        nombre.contains(busqueda) || apellido.contains(busqueda) || username == busqueda

    override fun asignarID(identificador: Int) {
        id = identificador
    }

    override fun id(): Int = id

    override fun estaBienConstruido() {
        validarNombre()
        validarApellido()
        validarUsername()
        validarEmail()
        validarIdioma()
        validarFechaNacimiento()
        validarPalabrasPorMinuto()
        validarCriterio()
        validarTipoLector()
    }

    fun validarNombre() {
        if (nombre.isBlank()) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: NOMBRE")
    }

    fun validarApellido() {
        if (apellido.isBlank()) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: APELLIDO")
    }

    fun validarUsername() {
        if (username.isBlank()) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: USERNAME")
    }

    fun validarEmail() {
        if (email.isBlank()) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: EMAIL")
    }

    fun validarIdioma() {
        if (idioma == null) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: IDIOMA")
    }

    fun validarFechaNacimiento() {
        if (fechaNacimiento == null) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: FECHA DE NACIMIENTO")
    }

    fun validarPalabrasPorMinuto() {
        if ((palabrasPorMinuto < 0) || (palabrasPorMinuto == null)) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: PALABRAS POR MINUTO")
    }

    fun validarCriterio() {
        if (criterioDeBusqueda == null) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: CRITERIO DE BÚSQUEDA")
    }

    fun validarTipoLector() {
        if (tipoLector == null) throw BusinessException("ERROR DE CONSTRUCCIÓN EN: TIPO DE LECTOR")
    }
}