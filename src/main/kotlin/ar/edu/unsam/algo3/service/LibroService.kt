package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.dominio.Libro
import ar.edu.unsam.algo3.errores.*
import ar.edu.unsam.algo3.repositorio.RepoLibro
import ar.edu.unsam.algo3.repositorio.RepoRecomendacion
import ar.edu.unsam.algo3.serializacion.toDTO
import org.springframework.stereotype.Service

@Service
class LibroService(
    val repositorioLibros: RepoLibro,
    val repositorioRecomendacion: RepoRecomendacion
) {

    fun libros() = repositorioLibros.todasLasInstancias()

    fun buscarLibros(busqueda:String) = repositorioLibros.todasLasInstancias().filter { it.condicionBusqueda(busqueda) }

    fun buscarLibro(id: Int) = repositorioLibros.obtenerPorId(id)

    fun nuevoLibro(libroBody: Libro): Libro {
        libroBody.esBestSeller()
        return repositorioLibros.agregar(libroBody)
    }

    /*
        Este método debería:
        1. Validar que el objeto recibido corresponde a un objeto bien construido (responsabilidad del objeto)
        2. Buscar el objeto en su repo correspondiente chequeando que no sea null
        3. Actualizar los datos del objeto a actualizar (responsabilidad del objeto de dominio.) y validar de vuelta (¿es realmente necesario?)
        4. Si todos estos pasos fueron exitosos, recién ahí tocar el repositorio, que es como nuestra base de datos, para no llenarla de datos falopa
        5. Devolver el objeto actualizado para hacer el pasamanos con el controller y que se muestre en el body del método http
     */
    fun actualizar(libroBody: Libro) = repositorioLibros.actualizar(crearLibroEditado(libroBody))

    fun crearLibroEditado(libro: Libro): Libro {
        val libroOriginal = repositorioLibros.obtenerPorId(libro.id)
        val libroDTO = Libro(
            titulo = libro.titulo,
            ediciones = libro.ediciones,
            autor = libro.autor,
            imagen = libro.imagen,
            paginas = libro.paginas,
            palabras = libro.palabras,
            ventasSemanales = libro.ventasSemanales,
            lecturaCompleja = libro.lecturaCompleja,
        ).apply {
            this.id = libroOriginal.id
            this.traducciones = libro.traducciones
        }
        return libroDTO
    }

    fun eliminar(libroBody: Libro) = repositorioLibros.borrar(libroBody)

    fun eliminarPorId(id: Int): Libro {
        if (this.libroEnRecomendaciones(id)){
                throw BusinessException("No se puede eliminar un libro que está en una recomendación.")
            }
        val libro = this.buscarLibro(id)
        return repositorioLibros.borrar(libro)
    }

    fun libroEnRecomendaciones(id: Int): Boolean {
        val libro = this.buscarLibro(id)
        return repositorioRecomendacion.todasLasInstancias().any{
            recomendacion ->  recomendacion.contieneLibroPorId(libro.id)}
    }
}