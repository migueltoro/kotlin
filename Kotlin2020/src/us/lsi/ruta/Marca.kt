package us.lsi.ruta

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import us.lsi.coordenadas.Coordenadas3D

data class Marca(val time: LocalTime, val coordenadas: Coordenadas3D) {

	public override fun toString(): String {
		return String.format("(%s,%.2f,%.2f,%.2f)",time,coordenadas.latitud,coordenadas.longitud,coordenadas.altitud)
	}

	companion object {
		fun parse(text: String): Marca {
			val campos = text.split((",").toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
			val time = LocalTime.parse(campos[0], DateTimeFormatter.ofPattern("HH:mm:ss"))
			val coordenadas = Coordenadas3D.of(campos[1].toDouble(),campos[2].toDouble(),campos[3].toDouble()/1000)
			return Marca.of(time, coordenadas)
		}
		fun of(time: LocalTime, coordenadas: Coordenadas3D): Marca {
			return Marca(time, coordenadas)
		}
	}
}