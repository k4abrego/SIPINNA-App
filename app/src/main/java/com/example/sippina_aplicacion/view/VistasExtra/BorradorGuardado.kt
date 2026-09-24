package com.example.sippina_aplicacion.view.VistasExtra

import com.example.sippina_aplicacion.viewmodel.EstadoReporte

data class BorradorGuardado(
    val id: String,
    val creadoEn: Long,
    val actualizadoEn: Long,
    val estado: EstadoReporte
)
