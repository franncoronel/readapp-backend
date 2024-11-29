package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dominio.*
import ar.edu.unsam.algo3.serializacion.UsuarioDTO
import ar.edu.unsam.algo3.serializacion.UsuarioInformacionDTO
import ar.edu.unsam.algo3.errores.*
import ar.edu.unsam.algo3.service.UsuarioService
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
@DisplayName("Dado un controller de usuarios")
class UsuarioControllerSpec(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var usuarioService: UsuarioService

    private val usuariosMock = mutableListOf<Usuario>().apply {
        add(Usuario("admin", "admin", "admin", email = "prueba@gmail.com", rol=Rol.ADMINISTRADOR))
        add(Usuario("Victor", "Blanco", "viti", fotoPerfil = "viti.png", email = "prueba@gmail.com"))
        add(Usuario("Adrian", "Martinez", "maravilla", fotoPerfil = "maravilla.png", email = "prueba@gmail.com"))
        add(Usuario("Jose", "Sanchez", "garrafa", fotoPerfil = "garrafa.png", email = "prueba@gmail.com"))
        add(Usuario("Mauricio", "Serna", "chicho_siesta", fotoPerfil = "chicho.png", email = "prueba@gmail.com"))
    }

    @Test
    fun `puedo mockear una llamada al endpoint via get y me responde correctamente` () {
        `when`(usuarioService.usuarios()).thenReturn(usuariosMock)
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/usuarios"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].nombre").value("admin"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[4].apellido").value("Serna"))
    }

    @Test
    fun `mockear la búsqueda por un id válido devuelve un usuario` () {
        `when`(usuarioService.buscarUsuario(2)).thenReturn(usuariosMock[1])
        mockMvc
            .perform(MockMvcRequestBuilders.get("/usuarios/2"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("viti"))
    }

    fun `puedo actualizar la información de un usuario`() {
        val usuarioInfoDTO = UsuarioInformacionDTO("Adrian",
                                                    "Martinez",
                                                    "maravilla",
                                                    "mav@gmail.com",
                                                    "25-06-2009",
                                                    10, 
                                                    listOf<String>(),
                                                    "Promedio",
                                                    3,
                                                    10,
                                                    5               
                                                )
        `when`(usuarioService.actualizarInformacion(3, usuarioInfoDTO)).thenReturn(usuariosMock[2])

        mockMvc.perform(
            MockMvcRequestBuilders.put("/usuarios/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(usuarioInfoDTO))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("maravilla"))
    }

    fun `puedo agregar un libro leido`() {
        `when`(usuarioService.agregarLibroLeido(4, 100)).thenReturn(usuariosMock[3])

        mockMvc.perform(
            MockMvcRequestBuilders.put("/usuarios/4/agregar-libro-leido")
                .contentType(MediaType.APPLICATION_JSON)
                .content("100")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("garrafa"))
    }

    @Test
    fun `puedo eliminar un libro leido`() {
        `when`(usuarioService.eliminarLibroLeido(1, 100)).thenReturn(usuariosMock[0])

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/usuarios/1/eliminar-libro-leido/100")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin"))
    }

    fun `puedo agregar un libro a leer`() {
        `when`(usuarioService.agregarLibroALeer(4, 100)).thenReturn(usuariosMock[3])

        mockMvc.perform(
            MockMvcRequestBuilders.put("/usuarios/4/agregar-libro-a-leer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("100")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("garrafa"))
    }
 
    @Test
    fun `puedo eliminar un libro a leer`() {
        `when`(usuarioService.eliminarLibroALeer(1, 14)).thenReturn(usuariosMock[0])

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/usuarios/1/eliminar-libro-a-leer/14")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin"))
    }

    @Test
    fun `puedo actualizar las recomendaciones a valorar de un usuario`() {
        val recomendacionesIds = listOf(1, 2, 3)
        `when`(usuarioService.actualizarRecomendacionesAValorar(1, recomendacionesIds)).thenReturn(usuariosMock[0])

        mockMvc.perform(
            MockMvcRequestBuilders.put("/usuarios/1/actualizar-recomendaciones-a-valorar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(recomendacionesIds))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin"))
    }

    @Test
    fun `puedo actualizar los amigos de un usuario`() {
        val amigosIds = listOf(10, 11)
        `when`(usuarioService.actualizarAmigos(1, amigosIds)).thenReturn(usuariosMock[0])

        mockMvc.perform(
            MockMvcRequestBuilders.put("/usuarios/1/actualizar-amigos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(amigosIds))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin"))
    }
}