package us.lsi.ruta

import java.time.temporal.ChronoUnit

class Intervalo(val principio: Marca, val fin: Marca) {

	companion object {
		fun of(principio: Marca, fin: Marca): Intervalo {
			return Intervalo(principio, fin)
		}
	}

	val desnivel: Double = this.fin.coordenadas.altitud - this.principio.coordenadas.altitud
	val longitud: Double = this.principio.coordenadas.distancia(this.fin.coordenadas)
	val tiempo: Double = this.principio.time.until(this.fin.time, ChronoUnit.SECONDS) / 3600.0
	val velocidad: Double = this.longitud / this.tiempo

	override
	fun toString(): String {
		return String.format("(%s,%s)", this.principio.toString(), this.fin.toString())
	}

}