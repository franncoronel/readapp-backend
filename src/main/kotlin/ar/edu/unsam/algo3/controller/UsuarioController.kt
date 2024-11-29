package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dominio.Usuario
import ar.edu.unsam.algo3.serializacion.UsuarioDTO
import ar.edu.unsam.algo3.serializacion.UsuarioInformacionDTO
import ar.edu.unsam.algo3.serializacion.fromDTO
import ar.edu.unsam.algo3.serializacion.toDTO
import ar.edu.unsam.algo3.service.UsuarioService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
class UsuarioController(val usuarioService: UsuarioService) {

    //Obtener todos los usuarios
    @GetMapping()
    fun usuarios() = usuarioService.usuarios()

    //Buscar un usuario por #id
    @GetMapping("/{id}")
    fun buscarUsuario(@PathVariable id: Int) = usuarioService.buscarUsuario(id)

    //Actualizar un informacion de un usuario
    @PutMapping("/{id}")
    fun actualizarInformacion(@PathVariable id: Int, @RequestBody usuarioBody: UsuarioInformacionDTO) : Usuario {
        return usuarioService.actualizarInformacion(id, usuarioBody)
    }

    //Agregar un libro a la lista de libros leidos
    @PutMapping("/{idUsuario}/agregar-libro-leido")
    fun agregarLibroLeido (@PathVariable idUsuario: Int, @RequestBody idLibro: Int) : UsuarioDTO {
        return usuarioService.agregarLibroLeido(idUsuario, idLibro).toDTO()
    }

    //Eliminar un libro a la lista de libros leido
    @DeleteMapping("/{idUsuario}/eliminar-libro-leido/{idLibro}")
    fun eliminarLibroLeido (@PathVariable idUsuario: Int, @PathVariable idLibro: Int) : UsuarioDTO {
        return usuarioService.eliminarLibroLeido(idUsuario, idLibro).toDTO()
    }
    
    //Agregar un libro a la lista de libros a leer
    @PutMapping("/{idUsuario}/agregar-libro-a-leer")
    fun actualizarLibrosALeer (@PathVariable idUsuario: Int, @RequestBody idLibro: Int) : UsuarioDTO {
        return usuarioService.agregarLibroALeer(idUsuario, idLibro).toDTO()
    }

    //Elminar un libro a la lista de libros a leer
    @DeleteMapping("/{idUsuario}/eliminar-libro-a-leer/{idLibro}")
    fun eliminarLibrosALeer (@PathVariable idUsuario: Int, @PathVariable idLibro: Int) : UsuarioDTO {
        return usuarioService.eliminarLibroALeer(idUsuario, idLibro).toDTO()
    }

    //Actualizar recomendaciones a valorar de un usuario
    @PutMapping("/{idUsuario}/actualizar-recomendaciones-a-valorar")
    fun actualizarRecomendacionesAValorar (@PathVariable idUsuario: Int, @RequestBody recomendacionesId: List<Int>) : UsuarioDTO {
        return usuarioService.actualizarRecomendacionesAValorar(idUsuario, recomendacionesId).toDTO()
    }

    //Actualizar amigos de un usuario
    @PutMapping("/{idUsuario}/actualizar-amigos")
    fun actualizarAmigos (@PathVariable idUsuario: Int, @RequestBody amigosId: List<Int>) : UsuarioDTO {
        return usuarioService.actualizarAmigos(idUsuario, amigosId).toDTO()
    }

}