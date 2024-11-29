package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.dominio.*
import ar.edu.unsam.algo3.serializacion.*
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.serializacion.UsuarioInformacionDTO
import ar.edu.unsam.algo3.serializacion.fromDTO
import org.springframework.stereotype.Component

const val ID_INICIAL = 1

@Component
class Repositorio<T : Entidad> {
    var entidades = mutableListOf<T>()
    var identificador = 1

    fun todasLasInstancias(): List<T> = entidades

    fun agregar(entidad: T): T {
//        entidad.estaBienConstruido()
        noFueAgregado(entidad)
        entidad.asignarID(identificador)
        identificador += 1
        entidades.add(entidad)
        return entidad
    }

    fun noFueAgregado(entidad: T) {
        if (entidades.find { it === entidad } != null)
            throw BusinessException("Este elemento ya se encuentra en el repositorio")
    }

    fun borrar(entidad: T): T {
        entidades.remove(obtenerPorId(entidad.id()))
        return entidad
    }

    fun actualizar(entidadNueva: T): T {
//        entidadNueva.estaBienConstruido()
        val objetoAModificar = obtenerPorId(entidadNueva.id())
        val indice = entidades.indexOf(objetoAModificar)
        entidades[indice] = entidadNueva
        return entidadNueva
    }

    fun obtenerPorId(idBuscado: Int): T {
        val busqueda = entidades.find { it.id() == idBuscado }
        if (busqueda == null)
            throw NotFoundException("No se encontró objeto con dicho ID")
        return busqueda
    }

    fun buscar(busqueda: String): List<T> = entidades.filter { it.condicionBusqueda(busqueda) }

    fun limpiar() {
        entidades.clear()
    }

    fun limpiarInit() {
        limpiar()
        identificador = ID_INICIAL
    }

    fun catidadEntidades() = entidades.size
}

interface Entidad {
    fun condicionBusqueda(busqueda: String): Boolean

    fun asignarID(identificador: Int)

    fun id(): Int

    fun estaBienConstruido()
}

@Component
object RepoUsuarios : Repositorio<Usuario>()

//Extension method para el caso de uso de la actualización del perfil de usuario. Cambia el objeto que
// esta dentro del repo sin cambiar su referencia.
//De esta manera se evita conflictos con el metodo esCreador.
fun RepoUsuarios.actualizarInformacionPersonal(usuarioConInfoVieja: Usuario, usuarioConInfoNueva : UsuarioInformacionDTO) : Usuario{
    val usuarioActualizado = usuarioConInfoNueva.fromDTO()
    return usuarioConInfoVieja.apply {
        nombre = usuarioActualizado.nombre
        apellido = usuarioActualizado.apellido
        username = usuarioActualizado.username
        fechaNacimiento = usuarioActualizado.fechaNacimiento
        email = usuarioActualizado.email
        palabrasPorMinuto = usuarioActualizado.palabrasPorMinuto
        tipoLector = usuarioActualizado.tipoLector
        criterioDeBusqueda = usuarioActualizado.criterioDeBusqueda
    }

}

@Component
object RepoRecomendacion : Repositorio<Recomendacion>()

@Component
object RepoLibro : Repositorio<Libro>()

@Component
object RepoAutor : Repositorio<Autor>()
fun RepoAutor.actualizarDesdeDTO(id: Int, autorModificado:AutorDTO) : Autor {
    val autorOriginal = this.obtenerPorId(id)
    return autorOriginal.apply {
        nombre = autorModificado.nombre
        apellido = autorModificado.apellido
        lenguaNativa = autorModificado.lenguaNativa
    }

}

@Component
object RepoCentros : Repositorio<CentroDeLectura>()