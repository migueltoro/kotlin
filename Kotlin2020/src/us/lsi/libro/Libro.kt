package us.lsi.libro

import java.util.function.Function
import java.util.HashMap
import java.util.HashSet
import java.util.stream.Collectors
import java.util.stream.Stream
import java.util.Arrays
import java.util.SortedMap
import java.util.TreeMap
import us.lsi.tools.File


object Libro {
	
	
	val palabrasHuecas:Set<String> = File.lineasFromFile("recursos/palabras_huecas.txt").toSet()
	val separadores = "[- ,;.\n()?¿!¡:\"]"
	
	val lineasAPalabras = { linea:String -> linea.split(Regex(Libro.separadores)).asSequence().filter({ !it.isEmpty() }) }

	fun numeroDeLineas(fichero: String): Int {
		return File.sequenceFromFile(fichero).count()
	}

	fun numeroDePalabrasDistintas(fichero: String): Int {
		return File.sequenceFromFile(fichero)
			.flatMap(lineasAPalabras)
			.distinct()
			.count()
	}

	fun numeroDePalabrasDistintasNoHuecas(fichero: String): Int {
		return File.sequenceFromFile(fichero)
			.flatMap(lineasAPalabras)
			.distinct()
			.filter({ p -> !Libro.palabrasHuecas.contains(p) })
			.count()
	}

	fun numeroDeLineasVacias(fichero: String): Int {
		return File.sequenceFromFile(fichero)
			.filter({ linea -> linea.length == 0 })
			.count()
	}

	fun longitudMediaDeLineas(fichero: String): Double {
		return File.sequenceFromFile(fichero)
			.map({ linea -> linea.length })
			.average()
	}

	fun primeraLineaConPalabra(fichero: String, palabra: String): String {
		return File.sequenceFromFile(fichero)
			.filter({ linea -> linea.contains(palabra) })
			.first()
	}

	fun primerNumeroLineaConPalabra(fichero: String, palabra: String): Int {
		return   File.sequenceFromFile(fichero).withIndex()
			.filter({ e -> e.value.contains(palabra) })
			.first()
			.index
	}

	fun lineaNumero(fichero: String, n: Int): String {
		return  File.sequenceFromFile(fichero).withIndex().filter({ e -> e.index.equals(n) })
			.first()
			.value
	}

	fun frecuenciasDePalabras(fichero: String): SortedMap<String,Int> {
		return File.sequenceFromFile(fichero)
			.flatMap({linea->lineasAPalabras(linea)})
			.groupingBy({p->p})
		    .fold(0){a,_->a+1}
		    .toSortedMap()
	}

	fun palabrasPorFrecuencias(fichero: String): SortedMap<Int, Set<String>> {
		return frecuenciasDePalabras(fichero).entries
			.groupingBy({e->e.value})
		    .fold(setOf<String>(),{a,e->a+e.key})
		    .toSortedMap()			
	}

	private fun lineasAPalabras2(nl: IndexedValue<String>): Sequence<IndexedValue<String>> {
		return nl.value.split(Regex(Libro.separadores)).asSequence()
			.map({ p -> IndexedValue(nl.index, p) })
	}

	fun lineasDePalabra(fichero: String): SortedMap<String, Set<Int>> {
		return File.sequenceFromFile(fichero).withIndex()
			.flatMap({linea -> lineasAPalabras2(linea) })
			.filter({ np -> np.value.length > 0 })
			.groupingBy({p->p.value})
			.fold(setOf<Int>()) {a,e:IndexedValue<String>->a+e.index}
		    .toSortedMap()		
	}

	
}