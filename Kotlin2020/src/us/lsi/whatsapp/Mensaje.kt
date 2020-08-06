package us.lsi.whatsapp

import java.time.LocalDate
import java.time.LocalTime
import java.util.regex.Matcher
import java.util.regex.Pattern
import us.lsi.tools.Preconditions
import java.time.format.DateTimeFormatter

data class Mensaje(val fecha: LocalDate, val hora: LocalTime, val usuario: String, val texto: String) {

	companion object {
		
		fun of(fecha: LocalDate, hora: LocalTime, usuario: String, texto: String): Mensaje {
			return Mensaje(fecha, hora, usuario, texto)
		}

		fun parse(mensaje: String): Mensaje {
			val m = Mensaje.pattern.matcher(mensaje)
			Preconditions.checkArgument(
				m.find() && m.groupCount() == 4,
				String.format("Formato incorrecto en grupos = %d, mensaje = %s", m.groupCount(), mensaje)
			)
			val fecha = LocalDate.parse(m.group("fecha"), DateTimeFormatter.ofPattern("d/M/y"))
			val hora = LocalTime.parse(m.group("hora"),DateTimeFormatter.ofPattern("H:m"))
			val usuario = m.group("usuario")
			val texto = m.group("texto")
			return Mensaje(fecha,hora,usuario,texto)
		}

		private val RE = "(?<fecha>\\d\\d?/\\d\\d?/\\d\\d?) (?<hora>\\d\\d?:\\d\\d) - (?<usuario>[^:]+): (?<texto>.+)"
		private val pattern = Pattern.compile(RE)

		@JvmStatic
		fun main(args: Array<String>) {
			val m =
				Mensaje.parse("26/02/16 16:16 - Sheldon: No tiene sentido, solo creo que es una buena idea para una camiseta.")
			println(m)
		}
	}

	override
	fun toString(): String {
		return String.format("%s,%s,%10s,%s", fecha.toString(), hora.toString(), usuario, texto)
	}

}