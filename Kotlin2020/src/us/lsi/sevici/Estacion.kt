package us.lsi.sevici

import us.lsi.coordenadas.Coordenadas2D
import us.lsi.tools.Preconditions

class Estacion(val numero: Int, val name: String, val slots: Int, val empty_slots: Int, val free_bikes: Int,
	val coordenadas: Coordenadas2D) {

	companion object {
		// 149_CALLE ARROYO,20,11,9,37.397829929383,-5.97567172039552	

		fun parse(linea: String): Estacion {
			val partes = linea.split((",").toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
			val sp = partes[0].split(("_").toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
			val numero = Integer.parseInt(sp[0])
			val name = sp[1]
			val slots = Integer.parseInt(partes[1])
			val empty_slots = Integer.parseInt(partes[2])
			val free_bikes = Integer.parseInt(partes[3])
			val coordenadas =
				Coordenadas2D.of(java.lang.Double.parseDouble(partes[4]), java.lang.Double.parseDouble(partes[5]))
			return Estacion(numero, name, slots, empty_slots, free_bikes, coordenadas)
		}
		@JvmStatic
		fun main(args: Array<String>) {
			val e = Estacion.parse("149_CALLE ARROYO,20,11,9,37.397829929383,-5.97567172039552")
			println(e)
		}
	}

	init {
		Preconditions.checkArgument(slots >= 0, String.format("Slots deben ser mayor o igual que cero y es %d", slots))
		Preconditions.checkArgument(
			empty_slots >= 0,
			String.format("Empty_Slots deben ser mayor o igual que cero y es %d", empty_slots)
		)
		Preconditions.checkArgument(
			free_bikes >= 0,
			String.format("Free_Bikes deben ser mayor o igual que cero y es %d", free_bikes)
		)
	}

	override
	fun toString(): String {
		return String.format(
			"(%3d,%30s,%2d,%2d,%2d,%s)",
			numero,
			name,
			slots,
			empty_slots,
			free_bikes,
			coordenadas.toString()
		)
	}

}