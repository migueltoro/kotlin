package us.lsi.ruta

import java.util.Locale

object TestRuta {
	
	@JvmStatic
	fun main(args: Array<String>) {
		Locale.setDefault(Locale.US)
		val r = Ruta.data("recursos/ruta.csv")
		System.out.println(r)
		System.out.println(r.desnivelCrecienteAcumulado)
		System.out.println(r.desnivelDecrecienteAcumulado)
		System.out.println(r.longitud)
	}
}