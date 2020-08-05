package us.lsi.geometria

import us.lsi.geometria.Vector2D
import kotlin.math.*
import java.util.Locale
import java.awt.Shape
import java.awt.geom.Ellipse2D

data class Punto2D(val x: Double, val y: Double): Objeto2D, ShapeDeObjeto {
	
	companion object {	
		fun of(x:Double,y: Double): Punto2D {
			return Punto2D(x, y)
		}
		val origen = Punto2D(0.0,0.0)
		@JvmStatic
		fun main(args:Array<String>) {
			Locale.setDefault(Locale("en", "US"))
			val p1 = Punto2D.of(2.3,4.5)
			println(p1)
			println(p1.cuadrante)
			val r = Recta2D.ofPuntos(Punto2D.origen,Punto2D.of(1.0,0.0))
			val p2 = p1.simetrico(r)
			println(p2)
		}				
	}
	
	val distanciaAlOrigen: Double = Math.sqrt(this.x*this.x+this.y*this.y)
	val cuadrante: Cuadrante = if (this.x >= 0 && this.y >= 0) {
		Cuadrante.PRIMER_CUADRANTE;
	} else if (this.x <= 0 && this.y >= 0) {
		Cuadrante.SEGUNDO_CUADRANTE;
	} else if (this.x <= 0 && this.y <= 0) {
		Cuadrante.TERCER_CUADRANTE;
	} else {
		Cuadrante.CUARTO_CUADRANTE;
	}	
	val asVector: Vector2D = Vector2D(this.x, this.y)
	
	
	fun getDistanciaA(p: Punto2D): Double {
    	val dx = this.x-p.x
    	val dy = this.y-p.y
		return Math.sqrt(dx*dx+dy*dy)
	}

    operator fun plus(v: Vector2D): Punto2D {
    	return Punto2D.of(this.x+v.x,this.y+v.y)
    }
	
	operator fun minus(v: Vector2D): Punto2D {
    	return Punto2D.of(this.x-v.x,this.y-v.y)
    }
    
    operator fun  minus(v: Punto2D): Vector2D {
    	return Vector2D(this.x-v.x,this.y-v.y)
    }
	
	override
	fun  traslada(v: Vector2D): Punto2D{
		return this + v
	}
    
	override
	fun rota(p: Punto2D, angulo: Double): Punto2D {
		val v = minus(p).rota(angulo)
		return p +v
	}
	
	override	
	fun  homotecia(p: Punto2D, factor: Double): Punto2D {
		return p + Vector2D.ofPuntos(p, this) * factor
	}
	
	override
	fun proyectaSobre(r: Recta2D): Punto2D {
		return r.punto + (this - (r.punto)).proyectaSobre(r.vector)
	}
	
	override
	fun simetrico(r: Recta2D): Punto2D {
		val p = this.proyectaSobre(r)
		return (p.asVector * 2.0 - this.asVector).asPunto
	}
	
	override
	fun transform(xt: Function1<Double,Double>, yt:Function1<Double,Double>): Punto2D {
		return Punto2D.of(xt(this.x),yt(this.y));
	}
	
	override
	fun show(v:Ventana): Unit {
		v.g2?.fill(this.shape(Ventana.xt,Ventana.yt))
	}
	
	override
	fun shape(xt:Function1<Double,Double>, yt:Function1<Double,Double>): Shape {
		val t = this.transform(xt,yt)
		val sc = (t.minus(Vector2D.baseX * 5.0)).minus(Vector2D.baseY * 5.0)
		return Ellipse2D.Double(sc.x,sc.y,10.0,10.0)
	}
	
	override
	fun toString(): String {
    	return String.format("(%.2f,%.2f)",this.x,this.y);
    }
	
	
}