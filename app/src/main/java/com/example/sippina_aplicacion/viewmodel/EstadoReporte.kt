package com.example.sippina_aplicacion.viewmodel

data class EstadoReporte(
    val pasoActual:Int = 0,

    // Paso de la ubicación
    val opcionUbicacion :String = "",
    val latitud: Double = 19.432608, // ejemplo para probar
    val longitud: Double = -99.133209,
    val direccion: String = "",

    // Detalles del reporte
    val horarioAprox:String = "12:00",
    val cantidadMenores: Int = 1,
    val adultoInvolucrados : String = "No",
    val descripcionHechos:String = "",

    // Descripcion del menor
    val rangoEdad:String = "",
    val genero:String = "",
    val descripcionFisica:String = "",

    //Evidencia
    val tipoEvidenciaSeleccionada:String = "",

    // Privacidad
    val esAnonimo: Boolean = false,
    val recibirActualizaciones:Boolean = false

)
