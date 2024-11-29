package ar.edu.unsam.algo3.service

import ar.edu.unsam.algo3.dominio.Lenguaje
import org.springframework.stereotype.Service

@Service
class LenguajeService() {

    fun lenguajes() = Lenguaje.values().map { it.toJson() }
}