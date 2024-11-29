package ar.edu.unsam.algo3.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.test.assertEquals

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Dado un controller de recomendaciones")
class RecomendacionControllerSpec(@Autowired val mockMvc: MockMvc) {

    @Test
    fun `puedo mockear una llamada al endpoint via get y me responde correctamente` () {
        mockMvc
            .perform(get("/recomendaciones/1/usuario"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(jsonPath("$.[0].['titulo']").value("Ciencia Ficción"))
    }

    @Test
    fun `mockear la búsqueda por un id válido devuelve una recomendación` () {
        mockMvc
            .perform(get("/recomendaciones/3/1"))
            .andExpect(status().is2xxSuccessful)
            .andExpect(content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(jsonPath("$.titulo").value("Para mis amigos"))
    }

//    @Test
//    fun `mockear la búsqueda por un id inválido produce un error de usuario` () {
//        val mensajeDeError = mockMvc.perform(get("/recomendaciones/7"))
//            .andExpect(status().isBadRequest)
//            .andReturn().resolvedException?.message
//        assertEquals("No existe recomendación con el id: 7", mensajeDeError)
//
//    }
}