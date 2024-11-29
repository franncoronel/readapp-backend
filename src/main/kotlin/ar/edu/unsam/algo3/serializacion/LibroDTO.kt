package ar.edu.unsam.algo3.serializacion

import ar.edu.unsam.algo3.dominio.Autor
import ar.edu.unsam.algo3.dominio.Lenguaje
import ar.edu.unsam.algo3.dominio.Libro


data class LibroDTO (
    val id:Int,
    val titulo : String,
    val ediciones : Int,
    val autor : Autor,
    val imagen : String,
    val paginas : Int,
    val palabras : Int,
    val idiomas : MutableSet<Lenguaje>,
    val ventasSemanales : Int,
    val bestSeller : Boolean,
    val desafiante : Boolean
)

fun Libro.toDTO(): LibroDTO = LibroDTO(
    id = this.id,
    titulo = this.titulo,
    ediciones = this.ediciones,
    autor = this.autor,
    imagen = this.imagen,
    paginas = this.paginas,
    palabras = this.palabras,
    idiomas = this.idiomas(),
    ventasSemanales = this.ventasSemanales,
    bestSeller = this.esBestSeller(),
    desafiante = this.esDesafiante()
)

fun LibroDTO.fromDTO(libroDTO: LibroDTO): Libro {
    return Libro(
        titulo = libroDTO.titulo,
        ediciones = libroDTO.ediciones,
        autor = libroDTO.autor,
        imagen = libroDTO.imagen,
        paginas = libroDTO.paginas,
        palabras = libroDTO.palabras,
        ventasSemanales = this.ventasSemanales,
        lecturaCompleja = this.desafiante,
    ).apply {
        traducciones = (libroDTO.idiomas - libroDTO.autor.lenguaNativa).toMutableSet()
        id = libroDTO.id
    }
}