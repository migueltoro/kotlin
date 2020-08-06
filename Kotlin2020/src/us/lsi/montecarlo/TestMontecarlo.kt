package us.lsi.montecarlo

import java.util.Locale

object TestMontecarlo {
	
	@JvmStatic
	fun main(args: Array<String>) {
		Locale.setDefault(Locale.US)
		val m1 = Mano.random()
		val m2 = Mano.of("[7H,8H,3C,3S,6H]")
		val m3 = Mano.of("[10D,10H,10C,10S,5H]")
		println(m1)
		println(m2)
		println(m3)
	}
}