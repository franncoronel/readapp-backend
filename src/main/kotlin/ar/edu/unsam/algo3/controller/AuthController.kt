package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.serializacion.RespuestaLoginDTO
import ar.edu.unsam.algo3.serializacion.UsuarioLoginDTO
import ar.edu.unsam.algo3.service.AuthService
import ar.edu.unsam.algo3.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/autenticacion")
@CrossOrigin("*")
class AuthController(private val authService: AuthService, private val usuarioService: UsuarioService) {

    @PostMapping()
    fun verificarUsuario(@RequestBody usuarioLogeado : UsuarioLoginDTO): RespuestaLoginDTO =
        authService.verificarUsuario(usuarioLogeado)

}