package ar.edu.unsam.algo3.serializacion

import ar.edu.unsam.algo3.dominio.Autor
import ar.edu.unsam.algo3.dominio.Lenguaje

data class AutorDTO (
    val id: Int,
    val nombre: String,
    val apellido: String,
    val lenguaNativa: Lenguaje)

fun Autor.toDTO() : AutorDTO {
    return AutorDTO(
        id = this.id,
        nombre = this.nombre,
        apellido = this.apellido,
        lenguaNativa = this.lenguaNativa
    )
}