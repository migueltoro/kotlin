package us.lsi.sevici

import us.lsi.coordenadas.Coordenadas2D
import us.lsi.tools.File
import java.util.Locale

data class Red(val estaciones: List<Estacion>) {

	companion object {
		fun parse(fichero: String): Red {
			val estaciones = File.lineasFromFile(fichero)
				.drop(1)
				.map({ linea -> Estacion.parse(linea) })
			return Red(estaciones)
		}

		@JvmStatic
		fun main(args: Array<String>) {
			Locale.setDefault(Locale("en", "US"))
			val r = Red.parse("recursos/estaciones.csv")
//			System.out.println(r)
			println(r.estacionConNumero(250))
			println(r.estacionConNombre("CALLE DE SALVADOR ALLENDE"))
		}

	}

	val numero: Int = this.estaciones.size
	val estacionesConBicisDisponibles: Set<Estacion>
		get() {
			return this.estaciones.filter({ it.free_bikes > 0 }).toSet()
		}
	val ubicaciones: Set<Coordenadas2D>
		get() {
			return this.estaciones.map({ it.coordenadas }).toSet()
		}
	val estacionMasBicisDisponibles: Estacion?
		get() {
			return this.estaciones.maxBy({ it.free_bikes })
		}

	val estacionesPorBicisDisponibles: Map<Int, List<Estacion>>
		get() {
			return this.estaciones.groupBy({ it.free_bikes })
		}

	val numeroDeEstacionesPorBicisDisponibles: Map<Int, Int>
		get() {
			return this.estaciones.groupingBy({ e -> e.free_bikes }).fold(0) { a: Int, e: Estacion -> a + e.free_bikes }
		}

	fun estacionConNumero(numero: Int): Estacion? = this.estaciones.filter({ e -> e.numero == numero }).first()

	fun estacionConNombre(nombre: String): Estacion? = this.estaciones.filter({ e -> e.name == nombre }).first()

	fun estacionesConBicisDisponibles(n: Int): Set<Estacion> =
		this.estaciones.filter { e -> e.free_bikes >= n }.toSet()

	fun ubicacionEstacionesDisponibles(k: Int): Set<Coordenadas2D> =
		this.estaciones.filter({ e -> e.free_bikes >= k }).map({ e -> e.coordenadas }).toSet()

	override
	fun toString(): String {
		return estaciones.joinToString(separator = "\n", prefix = "", postfix = "")
	}

}