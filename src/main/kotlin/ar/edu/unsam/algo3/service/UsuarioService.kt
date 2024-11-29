package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.dominio.*
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.repositorio.RepoLibro
import ar.edu.unsam.algo3.repositorio.RepoRecomendacion
import ar.edu.unsam.algo3.repositorio.RepoUsuarios
import ar.edu.unsam.algo3.repositorio.actualizarInformacionPersonal
import ar.edu.unsam.algo3.serializacion.*
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class UsuarioService(   val repositorioDeUsuarios: RepoUsuarios,
                        val repositorioLibros: RepoLibro,
                        val repositorioRecomendaciones : RepoRecomendacion) {

    fun usuarios() = repositorioDeUsuarios.todasLasInstancias()

    fun buscarUsuario(id: Int) =
        repositorioDeUsuarios.obtenerPorId(id) ?: throw NotFoundException("No se encontró un usuario con el id: $id")

    /*
        Este método debería:
        1. Validar que el objeto recibido corresponde a un objeto bien construido (responsabilidad del objeto)
        2. Buscar el objeto en su repo correspondiente chequeando que no sea null
        3. Actualizar los datos del objeto a actualizar (responsabilidad del objeto de dominio.) y validar de vuelta (¿es realmente necesario?)
        4. Si todos estos pasos fueron exitosos, recién ahí tocar el repositorio, que es como nuestra base de datos, para no llenarla de datos falopa
        5. Devolver el objeto actualizado para hacer el pasamanos con el controller y que se muestre en el body del método http
     */

    fun actualizarInformacion(idUsuario: Int, usuarioBody: UsuarioInformacionDTO) : Usuario {
        val usuario = repositorioDeUsuarios.obtenerPorId(idUsuario)
        return repositorioDeUsuarios.actualizarInformacionPersonal(usuario,usuarioBody)
    }

    fun agregarLibroLeido(idUsuario: Int, idLibro: Int) : Usuario {
        val usuario  = repositorioDeUsuarios.obtenerPorId(idUsuario)
        val libroAAgregar = repositorioLibros.obtenerPorId(idLibro)
        usuario.agregarLibroLeido(libroAAgregar)
        return repositorioDeUsuarios.actualizar(usuario)
    }

    fun eliminarLibroLeido(idUsuario: Int, idLibro: Int) : Usuario {
        val usuario  = repositorioDeUsuarios.obtenerPorId(idUsuario)
        val libroAEliminar = repositorioLibros.obtenerPorId(idLibro)
        usuario.eliminarLibroLeido(libroAEliminar)
        return repositorioDeUsuarios.actualizar(usuario)
    }
    
    fun agregarLibroALeer(idUsuario: Int, idLibro: Int) : Usuario {
        val usuario  = repositorioDeUsuarios.obtenerPorId(idUsuario)
        val libroAAgregar = repositorioLibros.obtenerPorId(idLibro)
        usuario.agregarLibroALeer(libroAAgregar)
        return repositorioDeUsuarios.actualizar(usuario)
    }

    fun eliminarLibroALeer(idUsuario: Int, idLibro: Int) : Usuario {
        val usuario  = repositorioDeUsuarios.obtenerPorId(idUsuario)
        val libroAEliminar = repositorioLibros.obtenerPorId(idLibro)
        usuario.eliminarLibroALeer(libroAEliminar)
        return repositorioDeUsuarios.actualizar(usuario)
    }

    fun actualizarRecomendacionesAValorar (idUsuario : Int, recomendacionesId: List<Int>) : Usuario {
        val usuario  = repositorioDeUsuarios.obtenerPorId(idUsuario)
        usuario.recomendacionesAValorar.clear()
        val recomendacionesAgregar = recomendacionesId.map { repositorioRecomendaciones.obtenerPorId(it) }
        recomendacionesAgregar.forEach { usuario.agregarRecomendacionAValorar(it) }
        return repositorioDeUsuarios.actualizar(usuario)
    }

    fun actualizarAmigos(idUsuario: Int, amigosId: List<Int>): Usuario {
        val usuario = repositorioDeUsuarios.obtenerPorId(idUsuario)
        usuario.amigos.clear()
        val amigosActualizados = amigosId.map { repositorioDeUsuarios.obtenerPorId(it) }
        amigosActualizados.forEach { usuario.agregarAmigo(it) }
        return repositorioDeUsuarios.actualizar(usuario)
    }

    fun eliminar(usuarioBody: Usuario) = repositorioDeUsuarios.borrar(usuarioBody)

}
