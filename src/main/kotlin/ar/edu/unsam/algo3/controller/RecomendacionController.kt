package ar.edu.unsam.algo3.controller

import EdicionRecomendacionDTO
import ar.edu.unsam.algo3.dominio.Recomendacion
import ar.edu.unsam.algo3.service.RecomendacionService
import org.springframework.web.bind.annotation.*
import ar.edu.unsam.algo3.dominio.Valoracion
import ar.edu.unsam.algo3.serializacion.NuevaValoracionDTO

@RestController
@RequestMapping("/recomendaciones")
@CrossOrigin("*")
class RecomendacionController(
    val recomendacionService: RecomendacionService,
    ) {

    //Obtener todos las recomendaciones
    @GetMapping("/{idUsuarioLogueado}/usuario")
    fun recomendaciones(@PathVariable idUsuarioLogueado : Int) = recomendacionService.recomendaciones(idUsuarioLogueado)

    //Obtener todas las recomendaciones segun criterio de busqueda del usuario
    @GetMapping("/{idUsuarioLogueado}/usuario-criterio")
    fun recomendacionesCriterioBusqueda(@PathVariable idUsuarioLogueado : Int) =
        recomendacionService.recomendacionesCriterioBusqueda(idUsuarioLogueado)

    //Obtener todas las recomendaciones de un usuario
    @GetMapping("/{idUsuarioLogueado}/usuario-logueado")
    fun recomendacionesUsuarioLogueado(@PathVariable idUsuarioLogueado : Int) =
        recomendacionService.recomendacionesUsuarioLogueado(idUsuarioLogueado)

    //Buscar un recomendacion por id
    @GetMapping("/{id}/{idUsuarioLogueado}")
    fun buscarRecomendacion(@PathVariable id: Int, @PathVariable idUsuarioLogueado: Int) =
        recomendacionService.buscarRecomendacion(id, idUsuarioLogueado)

    //Actualizar una recomendacion
    @PutMapping("/{id}")
    fun actualizar(@RequestBody recomendacionEditada: EdicionRecomendacionDTO) : Recomendacion {
        return recomendacionService.actualizar(recomendacionEditada)
    }

    //Eliminar una recomendacion
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int) : Recomendacion {
        return recomendacionService.eliminar(id)
    }

    //Agregar una nueva valoracion a la recomendacion
    @PutMapping("/{idRecomendacion}/nueva-valoracion")
    fun agregarNuevaValoracion(@PathVariable idRecomendacion: Int,
                               @RequestBody valoracion: NuevaValoracionDTO
    ){
        return recomendacionService.agregarValoracion(idRecomendacion,valoracion)
    }
}