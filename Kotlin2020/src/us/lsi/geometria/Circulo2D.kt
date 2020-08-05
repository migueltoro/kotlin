package us.lsi.geometria


import us.lsi.tools.Preconditions
import java.util.Locale
import java.awt.Shape
import java.awt.geom.Ellipse2D

data class Circulo2D(val centro: Punto2D, val radio: Double) : Objeto2D, ShapeDeObjeto {
	
	val area: Double = Math.PI * this.radio * this.radio
	val perimetro: Double = 2.0 * Math.PI * this.radio
	
	init {
		Preconditions.checkArgument(this.radio >= 0, "El radio debe ser mayor o igual a cero")
	}
	
	companion object {
		fun of(centro: Punto2D, radio: Double): Circulo2D {
			return Circulo2D(centro, radio)
		}
		@JvmStatic
		fun main(args:Array<String>) {
			Locale.setDefault(Locale("en", "US"))
			val p1 = Punto2D.of(2.3,4.5)
			val c = Circulo2D.of(p1,3.0)
			println(c.area)
			println(c.perimetro)
		}	
	}
	

	override
	fun rota(p: Punto2D, angulo: Double): Circulo2D {
		return Circulo2D.of(this.centro.rota(p, angulo), this.radio)
	}

	override
	fun traslada(v: Vector2D): Circulo2D {
		return Circulo2D.of(this.centro.traslada(v), this.radio)
	}

	override
	fun homotecia(p: Punto2D, factor: Double): Circulo2D {
		return Circulo2D.of(this.centro.homotecia(p, factor), this.radio * factor)
	}

	override
	fun proyectaSobre(r: Recta2D): Segmento2D {
		val pc = this.centro.proyectaSobre(r)
		val u = r.vector.unitario
		return Segmento2D.of(pc + u * this.radio, pc + u * -this.radio)
	}

	override
	fun simetrico(r: Recta2D): Circulo2D {
		return Circulo2D.of(this.centro.simetrico(r), this.radio)
	}

	override
	fun transform(xt: Function1<Double, Double>, yt: Function1<Double, Double>): Circulo2D {
		val p = (this.centro + Vector2D.ofXY(1.0, 0.0) * this.radio).transform(xt, yt)
		val c = this.centro.transform(xt, yt)
		return Circulo2D.of(c, p.x - c.x)
	}

	override
	fun show(v: Ventana): Unit {
		v.g2?.draw(this.shape(Ventana.xt,Ventana.yt))
	}
	
	override
	fun shape(xt: Function1<Double,Double>, yt: Function1<Double,Double>): Shape {
		val ct = this.transform(xt,yt)
		val sc = ct.centro + Vector2D.ofXY(-1.0,-1.0) * ct.radio
		return Ellipse2D.Double(sc.x,sc.y,2*ct.radio,2*ct.radio);	
	}

	override
	fun toString(): String {
		return String.format("(%s,%.2f)", this.centro, this.radio)
	}

}