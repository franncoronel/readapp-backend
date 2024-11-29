package ar.edu.unsam.algo3.serializacion

import RecomendacionDTO
import ar.edu.unsam.algo3.dominio.*
import ar.edu.unsam.algo3.repositorio.RepoLibro
import toDTO
import java.time.LocalDate
import java.time.format.DateTimeFormatter


data class UsuarioDTO(
    val nombre: String,
    val apellido: String,
    val username: String,
    val fotoPerfil: String = "perfil-default.png",
    val email: String,
    var fechaNacimiento: String,
    val palabrasPorMinuto: Int,
    var criterioDeBusqueda: List<String>,
    var tipoLector: String,
    var id: Int,
    val librosLeidos: MutableList<LibroDTO>,
    val librosALeer: MutableList<LibroDTO>,
    val recomendacionesAValorar: MutableList<RecomendacionDTO>,
    val amigos : MutableList<AmigoDTO>,
    val minimo : Int,
    val maximo: Int
    )

fun Usuario.toDTO() = UsuarioDTO(
    nombre = this.nombre,
    apellido = this.apellido,
    username = this.username,
    fotoPerfil = this.fotoPerfil,
    email = this.email,
    fechaNacimiento = this.fechaNacimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
    palabrasPorMinuto = this.palabrasPorMinuto,
    criterioDeBusqueda = this.criterioDeBusqueda.nombre(),
    tipoLector = this.tipoLector.nombre(),
    id = this.id,
    librosLeidos = this.librosLeidos.map { it.toDTO() }.toMutableList(),
    librosALeer = this.librosALeer.map { it.toDTO() }.toMutableList(),
    recomendacionesAValorar = this.recomendacionesAValorar.map { it.toDTO(this) }.toMutableList(),
    amigos = this.amigos.map { it.toAmigoDTO() }.toMutableList(),
    minimo = this.criterioDeBusqueda.minimo(),
    maximo = this.criterioDeBusqueda.maximo()
)


fun criterioFromString(tipo: String, minimo:Int,maximo:Int): CriterioDeBusqueda {
        return when (tipo) {
            "Leedor" -> Leedor
            "Precavido" -> Precavido
            "Poliglota" -> Poliglota
            "Nativista" -> Nativista
            "Calculador" -> Calculador(minimo..maximo)
            "Demandante" -> Demandante
            "Experimentado" -> Experimentado
            "Cambiante" -> Cambiante
            else -> throw IllegalArgumentException("Tipo de criterio de búsqueda no reconocido: $tipo")
        }
    }

fun criterioFromListaDeString(criterios: List<String>, minimo:Int, maximo:Int): CriterioDeBusqueda {
    if (criterios.size > 1){
        val criteriosFromString = criterios.map{criterio -> criterioFromString(criterio, minimo, maximo) }
        val multiCriterio = MultiCriterio()
        criteriosFromString.forEach { criterio -> multiCriterio.agregarCriterio(criterio)}
        return multiCriterio
    }
    else {
        return criterioFromString(criterios.first(), minimo, maximo)
    }
}

fun lectorFromString(tipo: String): Lector {
        return when (tipo) {
            "Promedio" -> Promedio
            "Ansioso" -> Ansioso
            "Fanatico" -> Fanatico
            "Recurrente" -> Recurrente
            else -> throw IllegalArgumentException("Tipo de lector no reconocido: $tipo")
        }
    }

data class UsuarioInformacionDTO (
    val nombre: String,
    val apellido: String,
    val username: String,
    val email: String,
    var fechaNacimiento: String,
    val palabrasPorMinuto: Int,
    var criterioDeBusqueda: List<String>,
    var tipoLector: String,
    var id: Int,
    var minimo: Int,
    var maximo: Int
)

fun UsuarioInformacionDTO.fromDTO() : Usuario {
    val usuarioInformacion = this
    return Usuario (
        nombre = usuarioInformacion.nombre,
        apellido= usuarioInformacion.apellido,
        username= usuarioInformacion.username,
        email=usuarioInformacion.email,
        fechaNacimiento=LocalDate.parse(usuarioInformacion.fechaNacimiento,DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        palabrasPorMinuto=usuarioInformacion.palabrasPorMinuto,
        criterioDeBusqueda=criterioFromListaDeString(usuarioInformacion.criterioDeBusqueda,usuarioInformacion.minimo, usuarioInformacion.maximo),
        tipoLector=lectorFromString(usuarioInformacion.tipoLector)).apply {
            id=usuarioInformacion.id
    }
}