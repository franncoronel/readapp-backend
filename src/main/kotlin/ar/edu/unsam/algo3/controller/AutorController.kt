package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dominio.Autor
import ar.edu.unsam.algo3.serializacion.AutorDTO
import ar.edu.unsam.algo3.serializacion.toDTO
import ar.edu.unsam.algo3.service.AutorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/autores")
@CrossOrigin("*")
class AutorController(val autorService: AutorService) {

    //Obtener todos los autores
    @GetMapping()
    fun autores() = autorService.autores().map{it.toDTO()}

    //Obtener un libro por texto
    @GetMapping("/buscar")
    fun buscarAutores(@RequestParam buscar:String) = autorService.buscarAutores(buscar).map{it.toDTO()}

    //Buscar un autor por id
    @GetMapping("/{id}")
    fun buscarAutor(@PathVariable id: Int) =
        autorService.buscarAutor(id).toDTO()

    //Crear un nuevo autor
    @PostMapping()
    fun nuevoAutor(@RequestBody autorBody: Autor) : Autor {
        return autorService.nuevoAutor(autorBody)
    }

    //Actualizar un autor
    @PutMapping("/{id}")
    fun actualizar(@RequestBody autorBody: AutorDTO, @PathVariable id: Int) : Autor{
        return autorService.actualizarDatos(id, autorBody)
    }

    //Eliminar un autor
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int) : Autor {
        return autorService.eliminar(id)
    }
}