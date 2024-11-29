package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dominio.Autor
import ar.edu.unsam.algo3.dominio.Lenguaje
import ar.edu.unsam.algo3.serializacion.AutorDTO
import ar.edu.unsam.algo3.errores.*
import ar.edu.unsam.algo3.service.AutorService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.mockito.Mockito.*
import kotlin.test.assertEquals

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Dado un controller de autores")
class AutorControllerSpec(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var autService: AutorService

    private val autoresMock = mutableListOf<Autor>().apply {
        add(Autor(nombre = "Lucy", apellido = "Maud Montgomery", seudonimo = "", lenguaNativa = Lenguaje.INGLES, edad = 67, premios = 1))
        add(Autor(nombre = "Victor", apellido = "Hugo", seudonimo = "", lenguaNativa = Lenguaje.FRANCES, edad = 83, premios = 5))
        add(Autor(nombre = "James Matthew", apellido = "Barry", seudonimo = "", lenguaNativa = Lenguaje.INGLES, edad = 77, premios = 0))
        add(Autor(nombre = "Charles", apellido = "Lutwidge Dodgson", seudonimo = "Lewis Carroll", lenguaNativa = Lenguaje.INGLES, edad = 66, premios = 1))
        add(Autor(nombre = "Henry", apellido = "James", seudonimo = "", lenguaNativa = Lenguaje.INGLES, edad = 72, premios = 3))
    }

    @Test
    fun `puedo mockear una llamada al endpoint via get y me responde correctamente` () {
        `when`(autService.autores()).thenReturn(autoresMock)
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/autores"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].nombre").value("Lucy"))
    }

    @Test
    fun `mockear la búsqueda por un id válido devuelve un autor` () {
        `when`(autService.buscarAutor(2)).thenReturn(autoresMock[1])
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/autores/2"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Hugo"))
    }

    @Test
    fun `si no encuentra un autor, retorna una excepción de negocio` () {
        `when`(autService.buscarAutor(893)).thenThrow(NotFoundException("No se encontró objeto con dicho ID"))
        
        val mensajeError = mockMvc
            .perform(
                MockMvcRequestBuilders.get("/autores/893"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andReturn().resolvedException?.message

        assertEquals(mensajeError, "No se encontró objeto con dicho ID")
    }

    @Test
    fun `puedo mockear la busqueda de un texto y me retorna todos los autores con coincidencias` () {
        val autoresEncontradosMock = mutableListOf<Autor>().apply {
            add(Autor(nombre = "James Matthew", apellido = "Barry", seudonimo = "", lenguaNativa = Lenguaje.INGLES, edad = 77, premios = 0))
            add(Autor(nombre = "Henry", apellido = "James", seudonimo = "", lenguaNativa = Lenguaje.INGLES, edad = 72, premios = 3))
        }

        `when`(autService.buscarAutores("James")).thenReturn(autoresEncontradosMock)

        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/autores/buscar")
                .param("buscar", "James"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].nombre").value("James Matthew"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1].apellido").value("James"))
    }

    @Test
    fun `buscar autores con texto que no coincide con ningún autor`() {
        `when`(autService.buscarAutores("Tisbutascratch")).thenReturn(mutableListOf<Autor>())

        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/autores/buscar")
                .param("buscar", "Tisbutascratch"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0))
    }

    @Test
    fun `buscar un texto vacio retorna todos los autores`() {
        `when`(autService.buscarAutores("")).thenReturn(autoresMock)
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/autores/buscar")
                .param("buscar", ""))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5))
    }

    @Test
    fun `puedo actualizar un autor pasando su id` () {
        val autorDTO = AutorDTO(3, "Jose", "Luque", Lenguaje.ESPANIOL)
        val autorMock = Autor(nombre = "Jose", apellido = "Luque", lenguaNativa = Lenguaje.ESPANIOL)
        `when`(autService.actualizarDatos(3, autorDTO)).thenReturn(autorMock)

        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/autores/3")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectMapper().writeValueAsString(autorDTO))
            )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Jose")) 
            .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Luque"))
    }

    @Test
    fun `si se trata de actualizar un id inexistente, emite una excepcion` () {
        val autorDTO = AutorDTO(3900990, "Jose", "Luque", Lenguaje.ESPANIOL)
        `when`(autService.actualizarDatos(3900990, autorDTO)).thenThrow((NotFoundException("No se encontró objeto con dicho ID")))

        val mensajeError = mockMvc
            .perform(
                MockMvcRequestBuilders.put("/autores/3900990")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectMapper().writeValueAsString(autorDTO))
            )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andReturn().resolvedException?.message

        assertEquals(mensajeError, "No se encontró objeto con dicho ID")
    }

    fun `puedo mockear una llamada al endpoint delete para borrar un autor, y como no tiene libros me deja hacerlo` () {
        val autorMock = Autor(nombre = "Jose", apellido = "Luque", lenguaNativa = Lenguaje.ESPANIOL)
        `when`(autService.eliminar(3)).thenReturn(autorMock.apply { id=6 })

        mockMvc
            .perform(MockMvcRequestBuilders.delete("/autores/3"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
            .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Jose")) 
            .andExpect(MockMvcResultMatchers.jsonPath("$.apellido").value("Luque"))
    }
    
    fun `puedo mockear una llamada al endpoint delete para borrar un autor, que como tiene libros en sistema, me tira una excepcion` () {
        `when`(autService.eliminar(3)).thenThrow(BusinessException("Un autor con un libro cargado en la App no puede ser eliminado"))

        val mensajeError = mockMvc
            .perform(MockMvcRequestBuilders.delete("/autores/3"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn().resolvedException?.message

        assertEquals(mensajeError, "Un autor con un libro cargado en la App no puede ser eliminado")
    }
}