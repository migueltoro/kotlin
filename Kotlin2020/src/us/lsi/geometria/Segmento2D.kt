package us.lsi.geometria

import us.lsi.geometria.Punto2D
import java.util.Locale
import java.awt.Shape;
import java.awt.geom.Line2D;



data class Segmento2D(val p1: Punto2D, val p2: Punto2D) : Objeto2D, ShapeDeObjeto {

	companion object {
		fun of(p1: Punto2D, p2: Punto2D): Segmento2D {
			return Segmento2D(p1, p2)
		}

		@JvmStatic
		fun main(args: Array<String>) {
			Locale.setDefault(Locale("en", "US"))
			val p = Punto2D.of(2.0, 3.0)
			println(p)
			val p1 = Punto2D(3.4, 5.6)
			println(Segmento2D.of(p, p1))
		}
	}

	val vector: Vector2D = Vector2D.ofPuntos(this.p1, this.p2)


	override
	fun rota(p: Punto2D, angulo: Double): Segmento2D {
		return Segmento2D.of(this.p1.rota(p, angulo), this.p2.rota(p, angulo))
	}

	override
	fun traslada(v: Vector2D): Segmento2D {
		return Segmento2D.of(this.p1.traslada(v), this.p2.traslada(v))
	}

	override
	fun homotecia(p: Punto2D, factor: Double): Segmento2D {
		return Segmento2D.of(this.p1.homotecia(p, factor), this.p2.homotecia(p, factor))
	}

	override
	fun proyectaSobre(r: Recta2D): Segmento2D {
		return Segmento2D.of(this.p1.proyectaSobre(r), this.p2.proyectaSobre(r))
	}

	override
	fun simetrico(r: Recta2D): Segmento2D {
		return Segmento2D.of(this.p1.simetrico(r), this.p2.simetrico(r))
	}

	override
	fun transform(xt: Function1<Double, Double>, yt: Function1<Double, Double>): Segmento2D {
		return Segmento2D.of(this.p1.transform(xt, yt), this.p2.transform(xt, yt))
	}
	
	override
	fun show(v: Ventana): Unit {
		v.g2?.draw(this.shape(Ventana.xt,Ventana.yt));
	}
	
	override
	fun shape(xt: Function1<Double,Double>, yt: Function1<Double,Double>): Shape {
		val t =  this.transform(xt,yt)
		return Line2D.Double(t.p1.x, t.p2.y, t.p2.x, t.p2.y);
	}

	public override fun toString(): String {
		return String.format("(%s,%s)", this.p1, this.p2)
	}

}