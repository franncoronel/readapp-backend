package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dominio.Libro
import ar.edu.unsam.algo3.serializacion.LibroDTO
import ar.edu.unsam.algo3.serializacion.fromDTO
import ar.edu.unsam.algo3.service.LibroService
import ar.edu.unsam.algo3.serializacion.toDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/libros")
@CrossOrigin("*")
class LibroController(val libroService: LibroService) {

    //Obtener todos los libros
    @GetMapping()
    fun libros() = libroService.libros().map { it.toDTO() }

    //Obtener un libro por texto
    @GetMapping("/buscar")
    fun buscarLibros(@RequestParam buscar:String) = libroService.buscarLibros(buscar).map { it.toDTO() }

    //Buscar un libro por id
    @GetMapping("/{id}")
    fun buscarLibro(@PathVariable id: Int) =
        libroService.buscarLibro(id).toDTO()

    //Crear un nuevo libro
    @PostMapping()
    fun nuevoLibro(@RequestBody libroDTO: LibroDTO): Libro {
        return libroService.nuevoLibro(libroDTO.fromDTO(libroDTO))
    }

    //Actualizar un libro
    @PutMapping()
    fun actualizar(@RequestBody libroDTO: LibroDTO): Libro {
        return libroService.actualizar(libroDTO.fromDTO(libroDTO))
    }

    //Eliminar un libro
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable("id") id: Int): Libro {
        return libroService.eliminarPorId(id)
    }
}