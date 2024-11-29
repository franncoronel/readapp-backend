package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dominio.Autor
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.serializacion.EstadisticaDTO
import ar.edu.unsam.algo3.serializacion.InactivosDTO
import ar.edu.unsam.algo3.service.AdministracionService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertEquals


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Dado un controller de usuarios")
class AdministracionControllerSpec(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var admService: AdministracionService

    //endpoint /estadisticas
    @Test
    fun `puedo mockear una llamada al endpoint via get y me responde correctamente` () {
    val estadisticasMock = mutableListOf<EstadisticaDTO>().apply {
        add(EstadisticaDTO("Libros", 24))
        add(EstadisticaDTO("Centros", 5))
        add(EstadisticaDTO("Usuarios", 5))
        add(EstadisticaDTO("Recomendaciones", 15))
    }
    `when`(admService.estadisticas()).thenReturn(estadisticasMock)

        mockMvc
            .perform(MockMvcRequestBuilders.get("/administracion/estadisticas"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0][?(@.entidad == 'Libros')].dato").value(24))
    }

    //endpoint /centros-inactivos con resultado positivo
    @Test
    fun `puedo mockear una llamada al endpoint delete para borrar centros inactivos y me devuelve la cantidad eliminada` () {
        val centrosMock = InactivosDTO("Centros", 3)
        `when`(admService.centrosInactivos()).thenReturn(centrosMock)

        mockMvc
            .perform(MockMvcRequestBuilders.delete("/administracion/centros-inactivos"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.elementoBorrado").value("Centros"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cantidadBorrado").value(3))
    }

    //endpoint /centros-inactivos con resultado negativo
    @Test
    fun `puedo mockear una llamada al endpoint delete para borrar centros inactivos y como no hay me devuelve error` () {
        `when`(admService.centrosInactivos()).thenThrow(BusinessException("No hay centros de lectura inactivos que eliminar"))

        val mensajeError = mockMvc
            .perform(MockMvcRequestBuilders.delete("/administracion/centros-inactivos"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn().resolvedException?.message

        assertEquals(mensajeError, "No hay centros de lectura inactivos que eliminar")
    }

    //endpoint /usuarios-inactivos con resultado positivo
    @Test
    fun `puedo mockear una llamada al endpoint delete para borrar usuarios inactivos y me devuelve la cantidad eliminada` () {
        val usuariosMock = InactivosDTO("Usuarios", 5)
        `when`(admService.usuariosInactivos()).thenReturn(usuariosMock)

        mockMvc
            .perform(MockMvcRequestBuilders.delete("/administracion/usuarios-inactivos"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.elementoBorrado").value("Usuarios"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.cantidadBorrado").value(5))
    }

    //endpoint /usuarios-inactivos con resultado negativo
    @Test
    fun `puedo mockear una llamada al endpoint delete para borrar usuarios inactivos y como no hay me devuelve error` () {
        `when`(admService.usuariosInactivos()).thenThrow(BusinessException("No hay usuarios inactivos que eliminar"))

        val mensajeError = mockMvc
            .perform(MockMvcRequestBuilders.delete("/administracion/usuarios-inactivos"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn().resolvedException?.message

        assertEquals(mensajeError, "No hay usuarios inactivos que eliminar")
    }

    @Test
    fun `agregar autor con datos válidos` () {
        val autor = Autor()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/administracion/agregar-autor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(autor))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}