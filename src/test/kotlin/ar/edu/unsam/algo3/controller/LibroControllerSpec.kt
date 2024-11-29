package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dominio.Autor
import ar.edu.unsam.algo3.dominio.Lenguaje
import ar.edu.unsam.algo3.dominio.Libro
import ar.edu.unsam.algo3.serializacion.LibroDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.assertEquals


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Dado un controller de libros")

class LibroControllerSpec(@Autowired val mockMvc: MockMvc) {

    @Test
    fun `puedo mockear una llamada al endpoint via get y me responde correctamente`() {
        mockMvc
            .perform(get("/libros"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(jsonPath("$.[0].titulo").value("Rashomon"))
    }


    @Test
    fun `Se crea un nuevo libro y se lo agrega al repositorio`() {
        val autorLibroExistente = Autor(
            nombre = "Pedro",
            apellido = "Mairal",
            seudonimo = "",
            lenguaNativa = Lenguaje.ESPANIOL,
            edad = 52,
            premios = 1
        )
        val libroNuevo = LibroDTO(
            palabras = 75000,
            paginas = 220,
            ediciones = 1,
            ventasSemanales = 54690,
            autor = autorLibroExistente,
            titulo = "La uruguaya",
            imagen = "portada-lord-files.jpg",
            bestSeller = true,
            desafiante = true,
            id = 80,
            idiomas = mutableSetOf(Lenguaje.INGLES)
        )

        mockMvc
            .perform(
                post("/libros")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectMapper().writeValueAsString(libroNuevo))
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.titulo").value("La uruguaya"))
    }
}