package ar.edu.unsam.algo3.service
import ar.edu.unsam.algo3.serializacion.RespuestaLoginDTO
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.repositorio.RepoUsuarios
import ar.edu.unsam.algo3.serializacion.UsuarioLoginDTO
import org.springframework.stereotype.Service

@Service
class AuthService (val repositorioDeUsuarios: RepoUsuarios) {

    fun validaUsuarioLogueado(usuarioLogueado : UsuarioLoginDTO) =
        repositorioDeUsuarios.todasLasInstancias()
            .find{ it.username == usuarioLogueado.username && it.password == usuarioLogueado.password }

    fun verificarUsuario(usuarioLogueado : UsuarioLoginDTO) : RespuestaLoginDTO {
        val usuarioBuscado = validaUsuarioLogueado(usuarioLogueado)
            ?: throw BusinessException("Usuario o contraseña incorrectos")
        return RespuestaLoginDTO(usuarioBuscado.username,usuarioBuscado.id, usuarioBuscado.rol)
    }
}
