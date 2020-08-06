package us.lsi.montecarlo

import java.util.Arrays
import us.lsi.tools.Preconditions

data class Card(val palo: Int, val valor: Int) {


	val id: Int = palo * 4 + valor
	
	init {
		Preconditions.checkArgument(this.valor >= 0 && this.valor < 13, String.format("Valor no adecuado %d",this.valor))
		Preconditions.checkArgument(this.palo >= 0 && this.palo < 4, String.format("Palo no adecuado %d",this.palo))
	}

	companion object {
		fun parse(text: String): Card {
			val p = text[text.length - 1]
			val v = text.substring(0, text.length - 1)
			val palo = symbolsPalos.indexOf(p)
			val valor = nombreValores.indexOf(v)
			return Card(palo, valor)
		}

		fun of(id: Int): Card {
			Preconditions.checkArgument(id >= 0 && id < 52, String.format("No es posible el id %d", id))
			val palo = id % 4
			val valor = id % 13
			return Card(palo, valor)
		}

		fun of(valor: Int, palo: Int): Card {
			return Card(valor, palo)
		}

		val nombreValores = Arrays.asList<String>("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")
		val symbolsPalos = Arrays.asList<Char>('C', 'H', 'S', 'D')
		val nombrePalos = Arrays.asList<String>("clubs", "hearts", "spades", "diamonds")
	}


	override
	fun toString(): String {
		return String.format("%s%s",nombreValores[valor],symbolsPalos[palo])
	}

}