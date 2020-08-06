package us.lsi.montecarlo

import kotlin.random.Random

data class Mano(val cartas: List<Card>) : Comparable<Mano> {

	companion object {

		val numeroDeCartas = 5
		fun of(cartas: List<Card>): Mano {
			return Mano(cartas)
		}

		fun of(text: String): Mano {
			val txt = text.substring(1, text.length - 1)
			val partes = txt.split((",").toRegex()).filter({ !it.isEmpty() }).toList()
			println(text)
			println(partes)
			val cartas = partes.map({ x -> Card.parse(x) }).toList()
			return Mano.of(cartas)
		}

		fun random(): Mano {
			val cartas = ArrayList<Card>()
			for (i in 0 until numeroDeCartas) {
				val n = Random.nextInt(0, 51)
				val card = Card.of(n)
				cartas.add(card)
			}
			return Mano(cartas)
		}
	}
	
	override
	fun toString(): String {
		val m =  this.cartas.map({c->c.toString()}).joinToString(separator = ",", prefix = "[", postfix = "]")
		val jg = this.jugada
		val f = this.fuerza()
		return String.format("[%s,%s,%.2f]",m,jg,f)
	}

	var _frecuenciasDeValores: Map<Int, Int>? = null
	val frecuenciasDeValores: Map<Int, Int>
		get() {
			if (this._frecuenciasDeValores == null) {
				this._frecuenciasDeValores = cartas.groupingBy({ c -> c.valor }).eachCount()
			}
			return this._frecuenciasDeValores ?: this._frecuenciasDeValores!!
		}

	var _frecuenciasDePalos: Map<Int, Int>? = null
	val frecuenciasDePalos: Map<Int, Int>
		get() {
			if (this._frecuenciasDePalos == null) {
				this._frecuenciasDePalos = cartas.groupingBy({ c -> c.palo }).eachCount()
			}
			return this._frecuenciasDePalos ?: this._frecuenciasDePalos!!
		}

	var _son5ValoresConsecutivos: Boolean? = null
	val son5ValoresConsecutivos: Boolean
		get() {
			if (_son5ValoresConsecutivos == null) {
				val son5 = this.valoresOrdenadosPorFrecuencias.size == 5
				val ls = cartas.sortedBy({ c -> c.valor })
				this._son5ValoresConsecutivos =
					son5 && (0..ls.size - 2).all({ x -> ls[x + 1].valor - ls[x].valor == 1 })
			}
			return this._son5ValoresConsecutivos ?: this._son5ValoresConsecutivos!!
		}

	var _valoresOrdenadosPorFrecuencias: List<Int>? = null
	val valoresOrdenadosPorFrecuencias: List<Int>
		get() {
			if (this._valoresOrdenadosPorFrecuencias == null) {
				this._valoresOrdenadosPorFrecuencias = this.frecuenciasDeValores.entries.asSequence()
					.sortedByDescending({ it.value })
					.map({ it.key })
					.toList()
			}
			return this._valoresOrdenadosPorFrecuencias ?: this._valoresOrdenadosPorFrecuencias!!
		}


	var _palosOrdenadosPorFrecuencias: List<Int>? = null
	val palosOrdenadosPorFrecuencias: List<Int>
		get() {
			if (this._palosOrdenadosPorFrecuencias == null) {
				this._palosOrdenadosPorFrecuencias = this.frecuenciasDePalos.entries.asSequence()
					.sortedByDescending({ it.value })
					.map({ it.key })
					.toList()
			}
			return this._palosOrdenadosPorFrecuencias ?: this._palosOrdenadosPorFrecuencias!!
		}

	val esColor: Boolean
		get() {
			return frecuenciasDePalos[this.palosOrdenadosPorFrecuencias[0]] == 5
		}

	val esEscalera: Boolean
		get() {
			return this.son5ValoresConsecutivos
		}

	val esPoker: Boolean
		get() {
			val val0 = this.valoresOrdenadosPorFrecuencias.get(0)
			return this.frecuenciasDeValores[val0] == 4
		}

	val esEscaleraDeColor: Boolean
		get() {
			val pal0 = this.palosOrdenadosPorFrecuencias[0]
			return this.son5ValoresConsecutivos && this.frecuenciasDePalos[pal0] == 5
		}

	val esFull: Boolean
		get() {
			val val0 = this.valoresOrdenadosPorFrecuencias[0]
			val val1 = this.valoresOrdenadosPorFrecuencias[1]
			return this.frecuenciasDeValores[val0] == 3 && this.frecuenciasDeValores[val1] == 2
		}

	val esTrio: Boolean
		get() {
			val val0 = this.valoresOrdenadosPorFrecuencias[0]
			return this.frecuenciasDeValores[val0] == 3
		}

	val esDoblePareja: Boolean
		get() {
			val val0 = this.valoresOrdenadosPorFrecuencias[0]
			val val1 = this.valoresOrdenadosPorFrecuencias[1]
			return this.frecuenciasDeValores[val0] == 2 && this.frecuenciasDeValores[val1] == 2
		}

	val esPareja: Boolean
		get() {
			val val0 = this.valoresOrdenadosPorFrecuencias[0]
			return this.frecuenciasDeValores[val0] == 2
		}

	val esEscaleraReal: Boolean
		get() {
			return (this.esEscaleraDeColor && this.cartas.map({ x -> x.valor }).toSet<Any>().contains(12))
		}

	val esCartaMasAlta: Boolean = true

	val _jugada: Int
		get() {
			return this.jugadas.indexOf(true)
		}

	val jugada: Jugada
		get() {
			return Jugada.values()[this._jugada]
		}

	fun fuerza(mano: Mano, n: Int): Double {
		var gana = 0
		var pierde = 0
		var empata = 0
		for (i in 0 until n) {
			val mr = Mano.random()
			if (mano < mr) pierde++
			else if (mano > mr) gana++
			else empata++
		}
		return (gana.toDouble()) / (gana + pierde + empata)
	}

	fun fuerza(n: Int = 5000): Double {
		return fuerza(this, n)
	}

	override
	fun compareTo(other: Mano): Int {
		var r = -this._jugada.compareTo(other._jugada)
		if (r == 0) r = this.valoresOrdenadosPorFrecuencias[0].compareTo(other.valoresOrdenadosPorFrecuencias[0])
		return r
	}

	private val jugadas: List<Boolean>
		get() {
			return listOf(
				this.esEscaleraReal,
				this.esEscaleraDeColor,
				this.esPoker,
				this.esFull,
				this.esColor,
				this.esEscalera,
				this.esTrio,
				this.esDoblePareja,
				this.esPareja,
				this.esCartaMasAlta
			)
		}

}