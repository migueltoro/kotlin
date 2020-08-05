package us.lsi.ruta

import java.time.temporal.ChronoUnit
import us.lsi.tools.File

class Ruta(val marcas: List<Marca>) {

	companion object {
		fun data(fichero: String): Ruta {
			val marcas = File.lineasFromFile(fichero).map({ x -> Marca.parse(x) })
			return Ruta(marcas)
		}
	}

	operator fun get(i: Int): Intervalo {
		return Intervalo.of(this.marcas[i], this.marcas[i + 1])
	}

	val tiempo: Double
		get() {
			val n = this.marcas.size
			return this.marcas[0].time.until(this.marcas[n - 1].time, ChronoUnit.SECONDS) / 3600.0
		}
	val longitud: Double
		get() {
			val n = this.marcas.size
			return (0..n - 2).map({ i -> this[i] }).map({ x -> x.longitud }).sum()
		}

	val desnivelCrecienteAcumulado: Double
		get() {
			val n = this.marcas.size
			return (0..n - 2).map({ i -> this[i] }).filter({ e -> e.desnivel > 0 }).map({ e -> e.longitud }).sum()
		}

	val desnivelDecrecienteAcumulado: Double
		get() {
			val n = this.marcas.size
			return (0..n - 2).map({ i -> this[i] }).filter({ e -> e.desnivel > 0 }).map({ e -> e.longitud }).sum()
		}

	val velocidadMedia: Double
		get() {
			return this.longitud / this.tiempo
		}

	override
	fun toString(): String {
		return marcas.joinToString(separator = "\n", prefix = "", postfix = "")
	}

}