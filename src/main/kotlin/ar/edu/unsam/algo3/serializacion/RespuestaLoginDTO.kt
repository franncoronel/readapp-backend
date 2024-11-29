package ar.edu.unsam.algo3.serializacion

import ar.edu.unsam.algo3.dominio.Rol

data class RespuestaLoginDTO (
    val username: String,
    val id: Int,
    val rol: Rol
)