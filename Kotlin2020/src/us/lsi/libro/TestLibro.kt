package us.lsi.libro

import java.util.Locale
import java.nio.charset.Charset

object TestLibro {
	
	fun palabras() {
		val p = "acordarme, no ha mucho tiempo que vivía un hidalgo de los de"
		val ls = p.split(Regex(Libro.separadores))
		println(ls)
	}
	
	@JvmStatic
	fun main(args: Array<String>) {
		val charset = Charsets.UTF_16
		Locale.setDefault(Locale.US)
//		println(Libro.numeroDeLineas("recursos/quijote.txt"))
//		println(Libro.numeroDePalabrasDistintasNoHuecas("recursos/quijote.txt"))
		println(Libro.frecuenciasDePalabras("recursos/quijote.txt").entries
			.map({e->Pair(e.key.toByteArray(charset).toString(charset),e.value)}).joinToString(separator="\n"))
//		println(palabras())
	}

}