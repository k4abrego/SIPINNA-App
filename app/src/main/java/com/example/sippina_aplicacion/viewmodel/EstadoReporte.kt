package com.example.sippina_aplicacion.viewmodel

data class EstadoReporte(
    val pasoActual:Int = 0,

    // Paso de la ubicación
    val opcionUbicacion :String = "",
    val direccionText: String = "",

    // Detalles del reporte
    val horarioAprox:String = "12:00",
    val cantidadMenores: Int = 1,
    val adultoInvolucrados : String = "",
    val descripcionHechos:String = "",

    // Descripcion del menor
    val rangoEdad:String = "",
    val genero:String = "",
    val descripcionFisica:String = "",

    //Evidencia
    val tipoEvidenciaSeleccionada:String = "",

    // Privacidad
    val esAnonimo: Boolean = true,
    val recibirActualizaciones:Boolean = false

)
