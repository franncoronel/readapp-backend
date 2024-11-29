package ar.edu.unsam.algo3.serializable

import RecomendacionDTO
import ar.edu.unsam.algo3.dominio.*
import ar.edu.unsam.algo3.repositorio.RepoLibro
import ar.edu.unsam.algo3.serializacion.LibroDTO
import ar.edu.unsam.algo3.serializacion.UsuarioDTO
import ar.edu.unsam.algo3.serializacion.toDTO
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UsuarioDTOSpec: DescribeSpec ({
    isolationMode = IsolationMode.InstancePerTest
    describe("Dado un usuarioDTO"){
        val autor1 = Autor(
            nombre = "Isaac",
            apellido = "Asimov",
            seudonimo = "IA",
            lenguaNativa = Lenguaje.INGLES,
            edad = 72,
            premios = 5
        )
        val libro1 = Libro(
            palabras = 50000,
            paginas = 300,
            ediciones = 10,
            ventasSemanales = 5000,
            lecturaCompleja = true,
            autor = autor1,
            titulo = "Fundación"
        )

        val libroDTO = LibroDTO(
            autor = Autor("",""),
            bestSeller = true,
            desafiante = true,
            id = 1,
            idiomas = mutableSetOf(Lenguaje.INGLES,Lenguaje.ESPANIOL),
            imagen = "",
            titulo ="",
            palabras = 1,
            paginas = 1,
            ventasSemanales = 1,
            ediciones = 1
        )

        val recomendacionDTO = RecomendacionDTO (
            id = 1,
            idCreador = 1,
            esCreador = true,
            tiempoLectura = 1,
            titulo = "",
            detalle = "",
            publica = false,
            librosRecomendados = mutableListOf(libroDTO),
            cantidadLibros = 1,
            puntaje = 0.0,
            puedeValorar = true,
            valoraciones = mutableListOf()
        )

        val usuarioDTO = UsuarioDTO(
            nombre = "Carlos",
            apellido = "Bilardo",
            username = "Bidón",
            email = "salvador@gmail.com",
            palabrasPorMinuto = 200,
            criterioDeBusqueda = listOf("Leedor","Demandante","Experimentado"),
            tipoLector = "Promedio",
            id = 7,
            librosLeidos = mutableListOf(libroDTO),
            librosALeer = mutableListOf(libroDTO),
            recomendacionesAValorar = mutableListOf(recomendacionDTO),
            fechaNacimiento = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            amigos = mutableListOf(),
            minimo = 1,
            maximo = 2
        )
        RepoLibro.agregar(libro1)
        it("su conversión a objeto de dominio debería retornar un usuario con criterio MultiCriterio"){
            RepoLibro.entidades shouldContain libro1
            val multicriterio = MultiCriterio().apply {
                agregarCriterio(Demandante)
                agregarCriterio(Experimentado)
            }
            val bilardo = Usuario(criterioDeBusqueda = multicriterio).toDTO()
            bilardo.criterioDeBusqueda shouldContainAll listOf("Demandante","Experimentado")
        }
    }
})