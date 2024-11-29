package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.service.AdministracionService
import ar.edu.unsam.algo3.dominio.Autor
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/administracion")
@CrossOrigin("*")
class AdministracionController(
    val administracionService: AdministracionService,
) {

    //Obtener las estadisticas para el dashboard
    @GetMapping("/estadisticas")
    fun estadisticas() = administracionService.estadisticas()

    @DeleteMapping("/centros-inactivos")
    fun borrarCentrosInactivos() = administracionService.centrosInactivos()
    
    @DeleteMapping("/usuarios-inactivos")
    fun borrarUsuariosInactivos() = administracionService.usuariosInactivos()

    @PostMapping("/agregar-autor")
    fun agregarAutor(@RequestBody autor:Autor) = administracionService.agregarAutores(listOf<Autor>(autor))
}