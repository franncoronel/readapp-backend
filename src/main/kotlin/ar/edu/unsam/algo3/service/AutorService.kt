package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.dominio.Autor
import ar.edu.unsam.algo3.errores.*
import ar.edu.unsam.algo3.serializacion.AutorDTO
import ar.edu.unsam.algo3.repositorio.RepoAutor
import ar.edu.unsam.algo3.repositorio.RepoLibro
import ar.edu.unsam.algo3.repositorio.actualizarDesdeDTO
import org.springframework.stereotype.Service

@Service
class AutorService(val repositorioAutores: RepoAutor,
                   val repositorioLibros: RepoLibro) {

    fun autores() = repositorioAutores.todasLasInstancias()

    fun buscarAutores(busqueda:String) = repositorioAutores.todasLasInstancias().filter { it.condicionBusqueda(busqueda) }

    fun buscarAutor(id: Int) = repositorioAutores.obtenerPorId(id)

    fun nuevoAutor(autorBody: Autor) = repositorioAutores.agregar(autorBody) //Debe llamar al proceso de administracion

    /*
        Este método debería:
        1. Validar que el objeto recibido corresponde a un objeto bien construido (responsabilidad del objeto)
        2. Buscar el objeto en su repo correspondiente chequeando que no sea null
        3. Actualizar los datos del objeto a actualizar (responsabilidad del objeto de dominio.) y validar de vuelta (¿es realmente necesario?)
        4. Si todos estos pasos fueron exitosos, recién ahí tocar el repositorio, que es como nuestra base de datos, para no llenarla de datos falopa
        5. Devolver el objeto actualizado para hacer el pasamanos con el controller y que se muestre en el body del método http
    */
    fun actualizarDatos(id: Int, autorBody: AutorDTO) :Autor {
        return repositorioAutores.actualizarDesdeDTO(id, autorBody)
    }

    fun eliminar(id: Int) : Autor {
        val autor = repositorioAutores.obtenerPorId(id)
        if (tieneUnlibro(autor)) {
            throw BusinessException("Un autor con un libro cargado en la App no puede ser eliminado")
        }
        return repositorioAutores.borrar(autor)
    }

    fun tieneUnlibro(autor:Autor) : Boolean {
        val libros = repositorioLibros.todasLasInstancias()
        return libros.any { it.autor == autor }
    }
}