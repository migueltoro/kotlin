package us.lsi.geometria

import 	java.util.Locale
import java.util.function.Function

data class Agregado2D(val objetos: MutableSet<Objeto2D>) : Objeto2D, ShowObjeto {

	companion object {
		
		fun empty(): Agregado2D {
			return Agregado2D(mutableSetOf<Objeto2D>())
		}

		fun of(vararg objetos: Objeto2D): Agregado2D {
			return Agregado2D(objetos.toMutableSet())
		}

		fun of(objetos: MutableSet<Objeto2D>): Agregado2D {
			return Agregado2D(objetos)
		}
		
		@JvmStatic
		fun main(args:Array<String>) {
			Locale.setDefault(Locale("en", "US"))
			val p1 = Punto2D.of(2.0,3.1)
			val p2 = Punto2D.of(3.0,-3.1)
			val s = Segmento2D.of(p1,p2)
			val a = Agregado2D.of(p1,p2,s)
			val r = Recta2D.ofPuntos(Punto2D.origen,Punto2D.of(1.0,0.0))
			val a2 = a.simetrico(r)
			println(a)
			println(a2)
		}	
	}

	val isEmpty: Boolean
		get() {
			return objetos.isEmpty()
		}

	fun size(): Int {
		return objetos.size
	}

	fun contains(o: Any): Boolean {
		return objetos.contains(o)
	}

	fun add(e: Objeto2D): Boolean {
		return objetos.add(e)
	}

	fun remove(o: Any): Boolean {
		return objetos.remove(o)
	}

	fun addAll(c: Collection<Objeto2D>): Boolean {
		return objetos.addAll(c)
	}

	override
	fun rota(p: Punto2D, angulo: Double): Agregado2D {
		return Agregado2D.of(this.objetos.map {x -> x.rota(p, angulo) }.toMutableSet())
	}
    
	override
	fun traslada(v: Vector2D): Agregado2D {
		return Agregado2D.of(objetos.map {x -> x.traslada(v)}.toMutableSet())
	}
	
    override
	fun homotecia(p: Punto2D, factor: Double): Agregado2D {
		return Agregado2D.of(this.objetos.map {x -> x.homotecia(p, factor)}.toMutableSet())
	}
    
	override
	fun proyectaSobre(r: Recta2D): Agregado2D {
		return Agregado2D.of(this.objetos.map{ x -> x.proyectaSobre(r) }.toMutableSet())
	}
    
	override
	fun simetrico(r: Recta2D): Agregado2D {
		return Agregado2D.of(this.objetos.map { x -> x.simetrico(r) }.toMutableSet())
	}
    
	override
	fun transform(xt: Function1<Double, Double>, yt: Function1<Double, Double>): Agregado2D {
		return Agregado2D.of(this.objetos.map { x -> x.transform(xt, yt) }.toMutableSet())
	}
	
	override
	fun show(v: Ventana): Unit {
		objetos.forEach {x->x.show(v)}
	}
	
	override
	fun toString(): String {
    	return this.objetos.joinToString(separator=",", prefix = "{", postfix = "}", transform = {x-> x.toString()})
    }
}