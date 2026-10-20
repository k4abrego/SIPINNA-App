package com.example.sippina_aplicacion
import com.example.sippina_aplicacion.view.PantallasReporte.MapeadorTerritorio
import com.example.sippina_aplicacion.view.perfilusuario.mapearEstatusAInt
import org.junit.Assert.assertEquals
import org.junit.Test

class MapeadorTerritorioYFuncionesTest {

    @Test
    fun `resolverIdTerritorio debe mapear municipios con acentos y mayusculas correctamente`() {
        assertEquals(126, MapeadorTerritorio.resolverIdTerritorio("Atizapán de Zaragoza", "Estado de México"))
        assertEquals(126, MapeadorTerritorio.resolverIdTerritorio("atizapan", "México"))

        assertEquals(33, MapeadorTerritorio.resolverIdTerritorio("Ecatepec de Morelos", "México"))
        assertEquals(58, MapeadorTerritorio.resolverIdTerritorio("NEZAHUALCOYOTL", "México"))
        assertEquals(2, MapeadorTerritorio.resolverIdTerritorio("Acambay", "México"))
        assertEquals(76, MapeadorTerritorio.resolverIdTerritorio("San Mateo Atenco", "México"))
        assertEquals(12, MapeadorTerritorio.resolverIdTerritorio("Atenco", "México"))

        assertEquals(13, MapeadorTerritorio.resolverIdTerritorio(null, null))
        assertEquals(13, MapeadorTerritorio.resolverIdTerritorio("", ""))
        assertEquals(13, MapeadorTerritorio.resolverIdTerritorio("Municipio Inexistente", "Otro Estado"))
    }

    @Test
    fun `resolverIdTerritorio debe retornar 13 por defecto si es nulo, vacio o desconocido`() {
        assertEquals(13, MapeadorTerritorio.resolverIdTerritorio(null, null))
        assertEquals(13, MapeadorTerritorio.resolverIdTerritorio("", ""))
        assertEquals(13, MapeadorTerritorio.resolverIdTerritorio("Municipio Inexistente", "Otro Estado"))
    }

    @Test
    fun `mapearEstatusAInt debe transformar correctamente los nombres de estado`() {
        assertEquals(0, mapearEstatusAInt("enviado"))
        assertEquals(0, mapearEstatusAInt("recibido"))
        assertEquals(1, mapearEstatusAInt("en revisión"))
        assertEquals(1, mapearEstatusAInt("en revision"))
        assertEquals(2, mapearEstatusAInt("en proceso"))
        assertEquals(2, mapearEstatusAInt("en atención"))
        assertEquals(3, mapearEstatusAInt("cerrado"))
        assertEquals(3, mapearEstatusAInt("concluido"))
        assertEquals(0, mapearEstatusAInt("estado_desconocido"))
    }
}