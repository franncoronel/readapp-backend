package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.dominio.*
import ar.edu.unsam.algo3.repositorio.*
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReadAppBootstrap( val repositorioDeUsuarios: RepoUsuarios,
                        val repositorioRecomendacion: RepoRecomendacion,
                        val repositorioAutores: RepoAutor,
                        val repositorioLibros: RepoLibro,
                        val repositorioCentros: RepoCentros) : InitializingBean {

//                                                          -USUARIOS-

    final val usuario1 = Usuario("admin", "admin", "admin", email = "prueba@gmail.com", rol=Rol.ADMINISTRADOR)
    final val usuario2 = Usuario("Victor", "Blanco", "viti", fotoPerfil = "viti.png", email = "prueba@gmail.com")
    final val usuario3 = Usuario("Adrian", "Martinez", "maravilla", fotoPerfil = "maravilla.png", email = "prueba@gmail.com")
    final val usuario4 = Usuario("Jose", "Sanchez", "garrafa", fotoPerfil = "garrafa.png", email = "prueba@gmail.com")
    final val usuario5 = Usuario("Mauricio", "Serna", "chicho_siesta", fotoPerfil = "chicho.png", email = "prueba@gmail.com")
    final val usuario6 = Usuario("Marcelo", "Delgado", "el_chel0", fotoPerfil = "chelo.png", email = "prueba@gmail.com")
    final val usuario7 = Usuario("Caro", "Pardiaco", "otsea_jelou_:$", fotoPerfil = "caro.png", email = "prueba@gmail.com")
    final val usuario8 = Usuario("Mono", "Monito", "mono_monito", fotoPerfil = "mono.png", email = "prueba@gmail.com")
    final val usuario9 = Usuario("Mono", "Kapanga", "mono-relojero-01", fotoPerfil = "mono-kpg.jpg", email = "prueba@gmail.com")
    final val usuario10 = Usuario("Mono", "Kapanga", "mono-relojero-02", fotoPerfil = "mono-kpg.jpg", email = "prueba@gmail.com")
    final val usuario11 = Usuario("Mono", "Kapanga", "mono-relojero-03", fotoPerfil = "mono-kpg.jpg", email = "prueba@gmail.com")
    final val usuario12 = Usuario("Mono", "Kapanga", "mono-relojero-04", fotoPerfil = "mono-kpg.jpg", email = "prueba@gmail.com")


//                                                        -RECOMENDACIONES-

    val recomendacion1 = Recomendacion(
        usuario1, "Ciencia Ficción",
        "Una colección increíble de libros que exploran el universo de la ciencia ficción. Perfectos para sumergirse en otros mundos cuando buscas escapar de la rutina diaria. ¡Imperdibles!",
        publica = false
    )

    val recomendacion2 = Recomendacion(
        usuario1, "Terror",
        "Si te encanta la fantasía épica, estos cinco libros de la saga te atraparán desde el primer capítulo. Llena de aventuras, magia y personajes inolvidables, ideal para largas tardes de lectura."
    )

    val recomendacion3 = Recomendacion(
        usuario1, "Para mis amigos",
        "Una serie de novelas históricas que retratan épocas fascinantes. Con detalles tan bien logrados que te sentirás transportado a otro tiempo y lugar. Para quienes disfrutan de lo verídico y lo épico."
    )

    val recomendacion4 = Recomendacion(
        usuario2, "Para las vacaciones",
        "Aquí van mis recomendaciones para cuando el ánimo está un poco bajo. Son historias llenas de esperanza, con personajes que encuentran luz incluso en los momentos más oscuros. Te levantarán el espíritu."
    )

    val recomendacion5 = Recomendacion(
        usuario2, "Comedia",
        "Libros breves pero profundos que me han dejado pensando días enteros. Cada uno con una perspectiva diferente sobre la vida, la muerte y las relaciones humanas. Perfectos para reflexionar."
    )

    val recomendacion6 = Recomendacion(
        usuario2, "Buenarda",
        "Libros breves pero profundos que me han dejado pensando días enteros. Cada uno con una perspectiva diferente sobre la vida, la muerte y las relaciones humanas. Perfectos para reflexionar."
    )

//                                                        -AUTORES-

    final val autor1 = Autor(
        nombre = "Ryuunosuke",
        apellido = "Akutagawa",
        seudonimo = "",
        lenguaNativa = Lenguaje.JAPONES,
        edad = 35,
        premios = 0
    )

    final val autor2 = Autor(
        nombre = "Stephanie",
        apellido = "Meyer",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 50,
        premios = 3
    )

    final val autor3 = Autor(
        nombre = "William",
        apellido = "Golding",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 81,
        premios = 1
    )

    final val autor4 = Autor(
        nombre = "Sergio",
        apellido = "Olguín",
        seudonimo = "",
        lenguaNativa = Lenguaje.ESPANIOL,
        edad = 53,
        premios = 2
    )

    final val autor5 = Autor(
        nombre = "Hernán",
        apellido = "Casciari",
        seudonimo = "",
        lenguaNativa = Lenguaje.ESPANIOL,
        edad = 52,
        premios = 5
    )

    final val autor6 = Autor(
        nombre = "George",
        apellido = "Orwell",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 46,
        premios = 0
    )

    final val autor7 = Autor(
        nombre = "Gabriel",
        apellido = "García Márquez",
        seudonimo = "",
        lenguaNativa = Lenguaje.ESPANIOL,
        edad = 87,
        premios = 2
    )

    final val autor8 = Autor(
        nombre = "Ray",
        apellido = "Bradbury",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 91,
        premios = 1
    )

    final val autor9 = Autor(
        nombre = "Miguel",
        apellido = "de Cervantes",
        seudonimo = "",
        lenguaNativa = Lenguaje.ESPANIOL,
        edad = 68,
        premios = 0
    )

    final val autor10 = Autor(
        nombre = "Harper",
        apellido = "Lee",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 89,
        premios = 1
    )

    final val autor11 = Autor(
        nombre = "Jorge Luis",
        apellido = "Borges",
        seudonimo = "",
        lenguaNativa = Lenguaje.ESPANIOL,
        edad = 86,
        premios = 0
    )

    final val autor12 = Autor(
        nombre = "Julio",
        apellido = "Cortázar",
        seudonimo = "",
        lenguaNativa = Lenguaje.ESPANIOL,
        edad = 69,
        premios = 0
    )

    final val autor13 = Autor(
        nombre = "Oscar",
        apellido = "Wilde",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 46,
        premios = 0
    )

    final val autor14 = Autor(
        nombre = "Homero",
        apellido = "",
        seudonimo = "",
        lenguaNativa = Lenguaje.GRIEGO,
        edad = 92,
        premios = 0
    )

    final val autor15 = Autor(
        nombre = "Jane",
        apellido = "Austen",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 41,
        premios = 0
    )

    final val autor16 = Autor(
        nombre = "Fiódor",
        apellido = "Dostoyevski",
        seudonimo = "",
        lenguaNativa = Lenguaje.RUSO,
        edad = 59,
        premios = 0
    )

    final val autor17 = Autor(
        nombre = "Albert",
        apellido = "Camus",
        seudonimo = "",
        lenguaNativa = Lenguaje.FRANCES,
        edad = 46,
        premios = 1
    )

    final val autor18 = Autor(
        nombre = "Franz",
        apellido = "Kafka",
        seudonimo = "",
        lenguaNativa = Lenguaje.ALEMAN,
        edad = 40,
        premios = 0
    )

    final val autor19 = Autor(
        nombre = "John",
        apellido = "Steinbeck",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 66,
        premios = 1
    )

    final val autor20 = Autor(
        nombre = "Jack",
        apellido = "Kerouac",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 47,
        premios = 0
    )

    final val autor21 = Autor(
        nombre = "Antoine",
        apellido = "de Saint-Exupéry",
        seudonimo = "",
        lenguaNativa = Lenguaje.FRANCES,
        edad = 44,
        premios = 0
    )

    final val autor22 = Autor(
        nombre = "James",
        apellido = "Joyce",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 58,
        premios = 0
    )

    final val autor23 = Autor(
        nombre = "Umberto",
        apellido = "Eco",
        seudonimo = "",
        lenguaNativa = Lenguaje.ITALIANO,
        edad = 84,
        premios = 1
    )

    final val autor24 = Autor(
        nombre = "Dan",
        apellido = "Brown",
        seudonimo = "",
        lenguaNativa = Lenguaje.INGLES,
        edad = 59,
        premios = 1
    )

//                                                          -LIBROS-

    final val libro1 = Libro(
        palabras = 50000,
        paginas = 196,
        ediciones = 5,
        ventasSemanales = 24690,
        lecturaCompleja = true,
        autor = autor1,
        titulo = "Rashomon",
        imagen = "portada-rashomon.jpg"
    ).apply { agregarTraduccion(setOf(Lenguaje.ARABE, Lenguaje.ALEMAN, Lenguaje.HINDI)) }

    final val libro2 = Libro(
        palabras = 120000,
        paginas = 500,
        ediciones = 12,
        ventasSemanales = 8555555,
        lecturaCompleja = false,
        autor = autor2,
        titulo = "Crepúsculo",
        imagen = "portada-crepusculo.jpg"
    ).apply { agregarTraduccion(setOf(Lenguaje.ARABE, Lenguaje.JAPONES, Lenguaje.ITALIANO)) }

    final val libro3 = Libro(
        palabras = 75000,
        paginas = 305,
        ediciones = 8,
        ventasSemanales = 54690,
        lecturaCompleja = true,
        autor = autor3,
        titulo = "El señor de las moscas",
        imagen = "portada-lord-files.jpg"
    ).apply { agregarTraduccion(setOf(Lenguaje.HINDI)) }

    final val libro4 = Libro(
        palabras = 60000,
        paginas = 288,
        ediciones = 4,
        ventasSemanales = 10,
        lecturaCompleja = true,
        autor = autor4,
        titulo = "La fragilidad de los cuerpos",
        imagen = "portada-fragilidad-cuerpos.jpg"
    ).apply { agregarTraduccion(setOf(Lenguaje.BENGALI, Lenguaje.ESPANIOL)) }

    final val libro5 = Libro(
        palabras = 80000,
        paginas = 420,
        ediciones = 7,
        ventasSemanales = 30040,
        lecturaCompleja = true,
        autor = autor5,
        titulo = "Messi es un perro",
        imagen = "portada-messi-perro.webp"
    ).apply { agregarTraduccion(setOf(Lenguaje.ARABE)) }

    final val libro6 = Libro(
        palabras = 100000,
        paginas = 328,
        ediciones = 11,
        ventasSemanales = 5000000,
        lecturaCompleja = true,
        autor = autor6,
        titulo = "1984",
        imagen = "portada-1984.webp"
    ).apply { agregarTraduccion(setOf(Lenguaje.RUSO, Lenguaje.PORTUGUES, Lenguaje.ALEMAN)) }

    final val libro7 = Libro(
        palabras = 150000,
        paginas = 417,
        ediciones = 9,
        ventasSemanales = 10000000,
        lecturaCompleja = true,
        autor = autor7,
        titulo = "Cien años de soledad",
        imagen = "portada-cien-anos.jpg"
    ).apply { agregarTraduccion(setOf(Lenguaje.JAPONES, Lenguaje.MANDARIN, Lenguaje.GRIEGO)) }

    final val libro8 = Libro(
        palabras = 95000,
        paginas = 256,
        ediciones = 6,
        ventasSemanales = 4000000,
        lecturaCompleja = true,
        autor = autor8,
        titulo = "Fahrenheit 451",
        imagen = "portada-fahrenheit.jpg"
    ).apply { agregarTraduccion(setOf(Lenguaje.ARABE, Lenguaje.ALEMAN)) }

    final val libro9 = Libro(
        palabras = 300000,
        paginas = 1023,
        ediciones = 18,
        ventasSemanales = 500000,
        lecturaCompleja = true,
        autor = autor9,
        titulo = "Don Quijote de la Mancha",
        imagen = "portada-don-quijote.webp"
    )

    final val libro10 = Libro(
        palabras = 85000,
        paginas = 281,
        ediciones = 5,
        ventasSemanales = 7500000,
        lecturaCompleja = false,
        autor = autor10,
        titulo = "Matar a un ruiseñor",
        imagen = "portada-matar-ruisenior.jpg"
    ).apply { agregarTraduccion(setOf(Lenguaje.RUSO)) }

    final val libro11 = Libro(
        palabras = 40000,
        paginas = 146,
        ediciones = 4,
        ventasSemanales = 300000,
        lecturaCompleja = true,
        autor = autor11,
        titulo = "El Aleph",
        imagen = "portada-aleph.webp"
    )

    final val libro12 = Libro(
        palabras = 150000,
        paginas = 600,
        ediciones = 10,
        ventasSemanales = 2000000,
        lecturaCompleja = true,
        autor = autor12,
        titulo = "Rayuela",
        imagen = "portada-rayuela.png"
    ).apply { agregarTraduccion(setOf(Lenguaje.ARABE, Lenguaje.ALEMAN, Lenguaje.HINDI)) }

    final val libro13 = Libro(
        palabras = 85000,
        paginas = 276,
        ediciones = 7,
        ventasSemanales = 1800000,
        lecturaCompleja = true,
        autor = autor13,
        titulo = "El retrato de Dorian Gray",
        imagen = "portada-retrato-dorian.jpg"
    )

    final val libro14 = Libro(
        palabras = 120000,
        paginas = 540,
        ediciones = 15,
        ventasSemanales = 950000,
        lecturaCompleja = true,
        autor = autor14,
        titulo = "La Odisea",
        imagen = "portada-odisea.jpg"
    ).apply { agregarTraduccion(setOf(Lenguaje.FRANCES, Lenguaje.ITALIANO, Lenguaje.PORTUGUES)) }

    final val libro15 = Libro(
        palabras = 110000,
        paginas = 432,
        ediciones = 8,
        ventasSemanales = 4200000,
        lecturaCompleja = true,
        autor = autor15,
        titulo = "Orgullo y prejuicio",
        imagen = "portada-orgullo-prejuicio.webp"
    )

    final val libro16 = Libro(
        palabras = 250000,
        paginas = 671,
        ediciones = 14,
        ventasSemanales = 850000,
        lecturaCompleja = true,
        autor = autor16,
        titulo = "Crimen y castigo",
        imagen = "portada-crimen-castigo.webp"
    ).apply { agregarTraduccion(setOf(Lenguaje.INGLES, Lenguaje.RUSO, Lenguaje.ARABE)) }

    final val libro17 = Libro(
        palabras = 80000,
        paginas = 185,
        ediciones = 6,
        ventasSemanales = 300000,
        lecturaCompleja = true,
        autor = autor17,
        titulo = "El extranjero",
        imagen = "portada-el-extranjero.webp"
    )

    final val libro18 = Libro(
        palabras = 60000,
        paginas = 201,
        ediciones = 5,
        ventasSemanales = 400000,
        lecturaCompleja = true,
        autor = autor18,
        titulo = "La metamorfosis",
        imagen = "portada-la-metamorfosis.webp"
    ).apply { agregarTraduccion(setOf(Lenguaje.ESPANIOL, Lenguaje.GRIEGO, Lenguaje.MANDARIN)) }

    final val libro19 = Libro(
        palabras = 120000,
        paginas = 464,
        ediciones = 9,
        ventasSemanales = 1700000,
        lecturaCompleja = true,
        autor = autor19,
        titulo = "Las uvas de la ira",
        imagen = "portada-las-uvas.webp"
    ).apply { agregarTraduccion(setOf(Lenguaje.ITALIANO, Lenguaje.PORTUGUES, Lenguaje.BENGALI)) }

    final val libro20 = Libro(
        palabras = 75000,
        paginas = 322,
        ediciones = 5,
        ventasSemanales = 900000,
        lecturaCompleja = true,
        autor = autor20,
        titulo = "En el camino",
        imagen = "portada-en-el-camino.jpeg"
    )

    final val libro21 = Libro(
        palabras = 30000,
        paginas = 112,
        ediciones = 3,
        ventasSemanales = 800000,
        lecturaCompleja = true,
        autor = autor21,
        titulo = "El principito",
        imagen = "portada-el-principito.webp"
    ).apply { agregarTraduccion(setOf(Lenguaje.JAPONES, Lenguaje.MANDARIN, Lenguaje.GRIEGO)) }

    final val libro22 = Libro(
        palabras = 265000,
        paginas = 732,
        ediciones = 11,
        ventasSemanales = 400000,
        lecturaCompleja = true,
        autor = autor22,
        titulo = "Ulises",
        imagen = "portada-ulises.webp"
    )

    final val libro23 = Libro(
        palabras = 130000,
        paginas = 512,
        ediciones = 6,
        ventasSemanales = 200000,
        lecturaCompleja = true,
        autor = autor23,
        titulo = "El nombre de la rosa",
        imagen = "portada-nombre-rosa.webp"
    ).apply { agregarTraduccion(setOf(Lenguaje.RUSO, Lenguaje.FRANCES)) }

    final val libro24 = Libro(
        palabras = 160000,
        paginas = 480,
        ediciones = 9,
        ventasSemanales = 3000000,
        lecturaCompleja = true,
        autor = autor24,
        titulo = "El código Da Vinci",
        imagen = "portada-codigo-da-vinci.webp"
    )
//                                                        -CENTROS DE LECTURA-

    val particular = Particular(maximoParticipantes = 30, porcentajeASuperar = 0.1)
    val santillana = Editorial(montoAAlcanzar = 4000, asisteElAutor = true)
    val kapelusz = Editorial(montoAAlcanzar = 40000, asisteElAutor = false)
    val bibliotecaNacional = Biblioteca(gastosFijos = 10000.0, metrosCuadrados = 60)
    val bibliotecaUnsam = Biblioteca(gastosFijos = 1000.0, metrosCuadrados = 20)

//                                              -RELACIÓN ENTRE LOS DATOS INSTANCIADOS-


    /* Este metodo construye entidades mas robustas, la idea es que convivan juntas
    y que se relacionen entre ellas.
     */
    fun construirBBDD () {
        usuario1.apply {
            agregarAmigo(usuario2)
            agregarAmigo(usuario3)
            agregarAmigo(usuario4)
            agregarAmigo(usuario5)
            agregarAmigo(usuario6)
            agregarAmigo(usuario7)
            agregarAmigo(usuario8)
            agregarAmigo(usuario9)
            agregarLibroLeido(libro1)
            agregarLibroLeido(libro2)
            agregarLibroLeido(libro3)
            agregarLibroLeido(libro4)
            agregarLibroLeido(libro5)
            agregarLibroLeido(libro6)
            agregarLibroLeido(libro7)
            agregarLibroLeido(libro8)
            agregarLibroLeido(libro9)
            agregarLibroLeido(libro10)
            agregarLibroLeido(libro11)
            agregarLibroLeido(libro12)
            agregarLibroLeido(libro13)
            agregarLibroALeer(libro14)
            agregarLibroALeer(libro15)
        }

        usuario2.apply {
            agregarLibroLeido(libro1)
            agregarLibroLeido(libro2)
            agregarLibroLeido(libro3)
            agregarLibroLeido(libro4)
            agregarLibroLeido(libro5)
            agregarLibroLeido(libro6)
            agregarLibroLeido(libro7)
            agregarLibroLeido(libro8)
            agregarLibroLeido(libro9)
            agregarLibroLeido(libro10)
            agregarLibroLeido(libro11)
            agregarLibroLeido(libro12)
            agregarLibroLeido(libro13)
            agregarLibroLeido(libro14)
            agregarLibroLeido(libro15)
            agregarAmigo(usuario1)
        }

        usuario5.apply {
            agregarLibroLeido(libro1)
            agregarLibroLeido(libro3)
            agregarLibroLeido(libro5)
            agregarLibroALeer(libro6)
            agregarLibroALeer(libro7)
            agregarLibroALeer(libro8)
            tipoLector = Ansioso
        }

        recomendacion1.apply {
            agregarLibro(libro1, usuario1)
            agregarLibro(libro5, usuario1)
            agregarLibro(libro3, usuario1)
            agregarLibro(libro9, usuario1)
        }

        recomendacion2.apply {
            agregarLibro(libro13, usuario1)
            agregarLibro(libro4, usuario1)
            agregarLibro(libro10, usuario1)
            agregarLibro(libro12, usuario1)
            agregarLibro(libro11, usuario1)
        }

        recomendacion3.apply {
            agregarLibro(libro8, usuario1)
            agregarLibro(libro7, usuario1)
            agregarLibro(libro6, usuario1)
            agregarLibro(libro13, usuario1)
        }

        recomendacion4.apply {
            agregarLibro(libro1, usuario2)
            agregarLibro(libro2, usuario2)
            agregarLibro(libro3, usuario2)
        }

        recomendacion5.apply {
            agregarLibro(libro6, usuario2)
            agregarLibro(libro12, usuario2)
            agregarLibro(libro10, usuario2)
            agregarLibro(libro11, usuario2)
            agregarLibro(libro13, usuario2)
        }

        recomendacion6.apply {
            agregarLibro(libro1, usuario2)
            agregarLibro(libro4, usuario2)
            agregarLibro(libro7, usuario2)
        }

        bibliotecaNacional.apply {
            agregarFecha(LocalDate.now().plusDays(1))
        }

        bibliotecaUnsam.apply {
            agregarFecha(LocalDate.now().plusDays(1))
        }

    }


    fun crearRecomendaciones() {
        repositorioRecomendacion.limpiarInit()
        repositorioRecomendacion.apply {
            agregar(recomendacion1)
            agregar(recomendacion2)
            agregar(recomendacion3)
            agregar(recomendacion4)
            agregar(recomendacion5)
            agregar(recomendacion6)
        }
    }

    fun crearUsuarios() {
        repositorioDeUsuarios.limpiarInit()
        repositorioDeUsuarios.apply {
            agregar(usuario1)
            agregar(usuario2)
            agregar(usuario3)
            agregar(usuario4)
            agregar(usuario5)
            agregar(usuario6)
            agregar(usuario7)
            agregar(usuario8)
            agregar(usuario9)
            agregar(usuario10)
            agregar(usuario11)
            agregar(usuario12)
        }
    }

    fun crearAutores() {
        repositorioAutores.limpiarInit()
        repositorioAutores.apply {
            agregar(autor1)
            agregar(autor2)
            agregar(autor3)
            agregar(autor4)
            agregar(autor5)
            agregar(autor6)
            agregar(autor7)
            agregar(autor8)
            agregar(autor9)
            agregar(autor10)
            agregar(autor11)
            agregar(autor12)
            agregar(autor13)
            agregar(autor14)
            agregar(autor15)
            agregar(autor16)
            agregar(autor17)
            agregar(autor18)
            agregar(autor19)
            agregar(autor20)
            agregar(autor21)
            agregar(autor22)
            agregar(autor23)
            agregar(autor24)

        }
    }

    fun crearLibros() {
        repositorioLibros.limpiarInit()
        repositorioLibros.apply {
            agregar(libro1)
            agregar(libro2)
            agregar(libro3)
            agregar(libro4)
            agregar(libro5)
            agregar(libro6)
            agregar(libro7)
            agregar(libro8)
            agregar(libro9)
            agregar(libro10)
            agregar(libro11)
            agregar(libro12)
            agregar(libro13)
            agregar(libro14)
            agregar(libro15)
            agregar(libro16)
            agregar(libro17)
            agregar(libro18)
            agregar(libro19)
            agregar(libro20)
            agregar(libro21)
            agregar(libro22)
            agregar(libro23)
            agregar(libro24)
        }
    }

    fun crearCentros() {
        repositorioCentros.limpiarInit()
        repositorioCentros.apply {
            agregar(particular)
            agregar(santillana)
            agregar(kapelusz)
            agregar(bibliotecaNacional)
            agregar(bibliotecaUnsam)
        }
    }

    override fun afterPropertiesSet() {
        construirBBDD()
        crearRecomendaciones()
        crearUsuarios()
        crearAutores()
        crearLibros()
        crearCentros()
    }
}
