import ar.edu.unsam.algo3.dominio.*
import ar.edu.unsam.algo3.dominio.Recomendacion
import ar.edu.unsam.algo3.serializacion.LibroDTO
import ar.edu.unsam.algo3.serializacion.toDTO

data class RecomendacionDTO (
    val id:Int,
    val idCreador:Int,
    val esCreador : Boolean,
    val tiempoLectura : Int,
    var titulo:String,
    var detalle:String,
    var publica:Boolean,
    var librosRecomendados:MutableList<LibroDTO>,
    var cantidadLibros:Int,
    var valoraciones:MutableList<Valoracion>,
    var puntaje:Double,
    var puedeValorar: Boolean
)

fun Recomendacion.toDTO(usuarioLogueado : Usuario): RecomendacionDTO = RecomendacionDTO(
    id = this.id,
    idCreador= this.creador.id(),
    esCreador = esElCreador(usuarioLogueado),
    tiempoLectura = tiempoLecturaRecomendacionNeto(usuarioLogueado),
    titulo = this.titulo,
    detalle = this.detalle,
    publica = this.publica,
    librosRecomendados = this.librosRecomendados.map { it.toDTO() }.toMutableList(),
    cantidadLibros = this.librosRecomendados.size,
    valoraciones = this.valoraciones.toMutableList(),
    puntaje = this.valoracionPromedio(),
    puedeValorar = this.puedeValorar(usuarioLogueado)
)

data class EdicionRecomendacionDTO (
    val id: Int,
    var titulo: String,
    var publica:Boolean,
    var detalle:String,
    var librosRecomendados: MutableSet<Libro>
)

