package us.lsi.tools

object Iterable {

	fun fibonacci() = sequence {
		var terms = Pair(0, 1)

		// this sequence is infinite
		while (true) {
			yield(terms.first)
			terms = Pair(terms.second, terms.first + terms.second)
		}
	}
	
	fun arithmetic(a: Int, r:Int) = sequence {
		var e = a
		yield(e)
		// this sequence is infinite
		while (true) {
			e = e+r
			yield(e)
		}
	}
	
	fun geometric(a: Int, r:Int) = sequence {
		var e = a
		yield(e)
		// this sequence is infinite
		while (true) {
			e = e*r
			yield(e)
		}
	}
	
	@JvmStatic
	fun main(args: Array<String>) {
		 val r = fibonacci().takeWhile({n-> n < 1000}).toList()
		 println(r)
		 val r1 = arithmetic(2,7).takeWhile({n-> n < 1000}).toList()
		 println(r1)
	}
}