package ar.edu.unsam.algo3.serializacion

import ar.edu.unsam.algo3.dominio.Usuario

data class AmigoDTO (
    val id: Int,
    val nombre: String,
    val apellido: String,
    val username: String,
    val foto: String)

fun Usuario.toAmigoDTO() : AmigoDTO {
    return AmigoDTO(
        id = this.id,
        nombre = this.nombre,
        apellido = this.apellido,
        username = this.username,
        foto = this.fotoPerfil
    )
}