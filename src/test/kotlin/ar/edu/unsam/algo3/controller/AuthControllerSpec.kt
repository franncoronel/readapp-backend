package ar.edu.unsam.algo3.controller

import ar.edu.unsam.algo3.dominio.Rol
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.serializacion.RespuestaLoginDTO
import ar.edu.unsam.algo3.serializacion.UsuarioLoginDTO
import ar.edu.unsam.algo3.service.AuthService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.Mockito.*
import kotlin.test.assertEquals

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Dado un controller de autenticación...")

class AuthControllerSpec(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var authService: AuthService

    @Test
    fun `puedo mockear un ingreso exitoso y recibir la respuesta esperada`() {

        val usuarioLoginMock = UsuarioLoginDTO("usuarioPrueba", "12345")
        val respuestaLoginMock = RespuestaLoginDTO("usuarioPrueba", 1, Rol.ADMINISTRADOR)
        /* Servicio mockeado */
        `when`(authService.verificarUsuario(usuarioLoginMock)).thenReturn(respuestaLoginMock)

        mockMvc
            .perform(
                post("/autenticacion")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectMapper().writeValueAsString(usuarioLoginMock))
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType(MediaType.APPLICATION_JSON)))
            .andExpect(jsonPath("$.username").value("usuarioPrueba"))
    }

    @Test
    fun `puedo mockear un intento de ingreso fallido y recibir la respuesta esperada`() {
        val usuarioLoginFallidoMock = UsuarioLoginDTO("usuarioPrueba", "contraseñaPrueba")
        `when`(authService.verificarUsuario(usuarioLoginFallidoMock)).thenThrow(BusinessException("Usuario o contraseña incorrectos"))

        val mensajeDeError = mockMvc
            .perform(
                post("/autenticacion")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ObjectMapper().writeValueAsString(usuarioLoginFallidoMock))
            )
            .andExpect(status().isBadRequest)
            .andReturn().resolvedException?.message

        assertEquals(mensajeDeError, "Usuario o contraseña incorrectos")
    }
}