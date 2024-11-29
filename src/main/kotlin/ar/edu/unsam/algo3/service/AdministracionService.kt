package ar.edu.unsam.algo3.service


import ar.edu.unsam.algo3.dominio.*
import ar.edu.unsam.algo3.dominio.Administrador
import ar.edu.unsam.algo3.dominio.BorrarCentrosExpirados
import ar.edu.unsam.algo3.dominio.Mail
import ar.edu.unsam.algo3.dominio.AgregarAutores
import ar.edu.unsam.algo3.dominio.Autor
import ar.edu.unsam.algo3.dominio.MailSender
import ar.edu.unsam.algo3.errores.BusinessException

import ar.edu.unsam.algo3.repositorio.*
import ar.edu.unsam.algo3.serializacion.EstadisticaDTO
import ar.edu.unsam.algo3.serializacion.InactivosDTO
import org.springframework.stereotype.Service

@Service

class AdministracionService(final val repositorioDeUsuarios: RepoUsuarios,
                            final val repositorioRecomendacion: RepoRecomendacion,
                            final val repositorioCentros: RepoCentros,
                            val repositorioLibros: RepoLibro,
                            val repositorioAutores: RepoAutor,
) {

    val administradorReadApp = Administrador()             //CONSULTAR ESTO
    val centroInactivos = BorrarCentrosExpirados(mailSenderMock, repositorioCentros)
    val usuariosInactivos = BorrarUsuariosInactivos(mailSenderMock,repositorioDeUsuarios,repositorioRecomendacion)

    fun estadisticas(): MutableList<EstadisticaDTO> {
        val recomendaciones = EstadisticaDTO("Recomendaciones", repositorioRecomendacion.catidadEntidades())
        val libros = EstadisticaDTO("Libros", repositorioLibros.catidadEntidades())
        val usuarios = EstadisticaDTO("Usuarios", repositorioDeUsuarios.catidadEntidades())
        val centros = EstadisticaDTO("Centros", repositorioCentros.catidadEntidades())
        val datos = mutableListOf(recomendaciones, libros, usuarios, centros)
        return datos
    }

    fun centrosInactivos(): InactivosDTO {
        val centrosAntes = repositorioCentros.catidadEntidades()
        administradorReadApp.administrar(listOf(centroInactivos))
        val centrosAhora = repositorioCentros.catidadEntidades()
        val centrosEliminados = centrosAntes - centrosAhora
        if (centrosEliminados < 1)
            throw BusinessException("No hay centros de lectura inactivos que eliminar")
        return InactivosDTO("Centros",centrosEliminados)
    }

    fun usuariosInactivos(): InactivosDTO {
        val usuarios = repositorioDeUsuarios.catidadEntidades()
        administradorReadApp.administrar(listOf(usuariosInactivos))
        val usuariosActuales = repositorioDeUsuarios.catidadEntidades()
        val usuariosEliminados = usuarios-usuariosActuales
        println(repositorioDeUsuarios.todasLasInstancias().map{ it.username})
        if (usuariosEliminados < 1)
            throw BusinessException ("No hay usuarios inactivos que eliminar")
        return InactivosDTO("Usuarios",(usuariosEliminados))
    }

    fun agregarAutores(autores:List<Autor>) {
        val agregarAutores = AgregarAutores(mailSenderMock, repositorioAutores, autores)
        administradorReadApp.administrar(listOf(agregarAutores))
    }

}

object mailSenderMock : MailSender {
    override fun sendMail(mail: Mail) {}
}