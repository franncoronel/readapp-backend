package ar.edu.unsam.algo3.serializacion

data class  EstadisticaDTO (
    val entidad : String,
    val dato : Int
)

data class InactivosDTO (
    val elementoBorrado : String,
    val cantidadBorrado : Int
) {}