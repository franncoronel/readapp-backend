package ar.edu.unsam.algo3.dominio

import com.fasterxml.jackson.annotation.JsonValue

enum class Lenguaje(val nombreCorregido: String) {
    INGLES ("Inglés"),
    ESPANIOL ("Español"),
    ALEMAN ("Alemán"),
    ITALIANO ("Italiano"),
    PORTUGUES("Portugués"),
    MANDARIN("Mandarín"),
    ARABE("Árabe"),
    RUSO("Ruso"),
    HINDI("Hindi"),
    FRANCES("Francés"),
    BENGALI("Bengalí"),
    GRIEGO("Griego"),
    JAPONES("Japonés");

    @JsonValue
    fun toJson(): String {
        return nombreCorregido
    }
}