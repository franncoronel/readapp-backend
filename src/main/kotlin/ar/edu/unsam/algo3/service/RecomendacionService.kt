package ar.edu.unsam.algo3.service

import EdicionRecomendacionDTO
import RecomendacionDTO
import ar.edu.unsam.algo3.dominio.Recomendacion
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.repositorio.RepoRecomendacion
import ar.edu.unsam.algo3.repositorio.RepoUsuarios
import org.springframework.stereotype.Service
import toDTO
import ar.edu.unsam.algo3.serializacion.NuevaValoracionDTO
import java.time.LocalDate

@Service
class RecomendacionService( val repositorioRecomendaciones: RepoRecomendacion,
                            val repositorioUsuarios: RepoUsuarios) {

    fun recomendaciones(idUsuarioLogueado : Int): List<RecomendacionDTO> {
        val usuarioLogueado = repositorioUsuarios.obtenerPorId(idUsuarioLogueado)
        val recomendacionesDominio = repositorioRecomendaciones.todasLasInstancias()
        return recomendacionesDominio.map{it.toDTO(usuarioLogueado)}
    }

    fun recomendacionesCriterioBusqueda(idUsuarioLogueado: Int): List<RecomendacionDTO> {
        val usuarioLogueado = repositorioUsuarios.obtenerPorId(idUsuarioLogueado)
        val todasLasRecomendaciones = repositorioRecomendaciones.todasLasInstancias()
        val recomendacionesPorCriterio = todasLasRecomendaciones.filter { usuarioLogueado.buscarRecomendacion(it) }
        return recomendacionesPorCriterio.map{it.toDTO(usuarioLogueado)}
    }

    fun recomendacionesUsuarioLogueado(idUsuarioLogueado: Int): List<RecomendacionDTO> {
        val usuarioLogueado = repositorioUsuarios.obtenerPorId(idUsuarioLogueado)
        val todasLasRecomendaciones = repositorioRecomendaciones.todasLasInstancias()
        val recomendacionUsuarioLogueado = todasLasRecomendaciones.filter { it.esElCreador(usuarioLogueado) }
        return recomendacionUsuarioLogueado.map{it.toDTO(usuarioLogueado)}
    }

    fun buscarRecomendacion(id: Int, idUsuarioLogueado : Int):RecomendacionDTO {
        val usuarioLogueado = repositorioUsuarios.obtenerPorId(idUsuarioLogueado)
        return repositorioRecomendaciones.obtenerPorId(id).toDTO(usuarioLogueado) ?: throw NotFoundException("No existe recomendación con el id: ${id}")
    }

    /*
        Este método debería:
        1. Validar que el objeto recibido corresponde a un objeto bien construido (responsabilidad del objeto)
        2. Buscar el objeto en su repo correspondiente chequeando que no sea null
        3. Actualizar los datos del objeto a actualizar (responsabilidad del objeto de dominio.) y validar de vuelta (¿es realmente necesario?)
        4. Si todos estos pasos fueron exitosos, recién ahí tocar el repositorio, que es como nuestra base de datos, para no llenarla de datos falopa
        5. Devolver el objeto actualizado para hacer el pasamanos con el controller y que se muestre en el body del método http
     */
    fun actualizar(recomendacionEditada: EdicionRecomendacionDTO) : Recomendacion {
        return repositorioRecomendaciones.actualizar(crearRecomendacionEditada(recomendacionEditada))
    }

    fun crearRecomendacionEditada(recomendacionEditada : EdicionRecomendacionDTO) : Recomendacion {
        val recomendacionOriginal = repositorioRecomendaciones.obtenerPorId(recomendacionEditada.id)
        return Recomendacion(
            creador = recomendacionOriginal.creador,
            titulo = recomendacionEditada.titulo,
            publica = recomendacionEditada.publica,
            detalle = recomendacionEditada.detalle).apply {
            id = recomendacionEditada.id
            librosRecomendados = recomendacionEditada.librosRecomendados
            valoraciones = recomendacionOriginal.valoraciones
            recomendacionObservers = recomendacionOriginal.recomendacionObservers
        }
    }

    fun eliminar(id: Int) : Recomendacion {
        val recomendacion = repositorioRecomendaciones.obtenerPorId(id)
        return repositorioRecomendaciones.borrar(recomendacion)
    }

    fun agregarValoracion(idRecomendacion: Int,valoracion : NuevaValoracionDTO) {
        val recomendacion = repositorioRecomendaciones.obtenerPorId(idRecomendacion)
        val usuario = repositorioUsuarios.obtenerPorId(valoracion.idCreador)
        return recomendacion.agregarOEditarValoracion(usuario,valoracion.valor,valoracion.comentario, fecha = LocalDate.now())
    }



}
