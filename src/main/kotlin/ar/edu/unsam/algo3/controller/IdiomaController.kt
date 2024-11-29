package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dominio.Lenguaje
import ar.edu.unsam.algo3.service.LenguajeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/lenguajes")
@CrossOrigin("*")
class LenguajeController(val lenguajeService: LenguajeService) {

    //Obtener todos los autores
    @GetMapping()
    fun lenguajes() = lenguajeService.lenguajes()
}