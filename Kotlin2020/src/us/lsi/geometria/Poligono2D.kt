package us.lsi.geometria

import java.awt.Shape
import java.awt.geom.GeneralPath
import us.lsi.tools.Preconditions

data class Poligono2D(val vertices: List<Punto2D>) : Objeto2D, ShapeDeObjeto {

	companion object {
		fun of(lp: List<Punto2D>): Poligono2D {
			return Poligono2D(lp)
		}

		fun triangulo(p1: Punto2D, p2: Punto2D, p3: Punto2D): Poligono2D {
			return Poligono2D(listOf(p1, p2, p3))
		}

		fun trianguloEquilatero(p1: Punto2D, lado: Vector2D): Poligono2D {
			return Poligono2D(listOf(p1, p1 + lado, p1 + lado.rota(Math.PI / 3)))
		}

		fun cuadrado(p: Punto2D, lado: Vector2D): Poligono2D {
			return Poligono2D(listOf(p, p + lado, p + lado + lado.ortogonal, p + lado.ortogonal))
		}

		fun rectangulo(p: Punto2D, base: Vector2D, altura: Double): Poligono2D {
			return Poligono2D(listOf(p, p + base, p + base + base.ortogonal * altura, p + base.ortogonal * altura))
		}

		fun rectanguloHorizontal(p: Punto2D, base: Double, altura: Double): Poligono2D {
			val p1 = p + Vector2D.ofXY(base, 0.0)
			val p2 = p + Vector2D.ofXY(base, altura)
			val p3 = p + Vector2D.ofXY(0.0, altura)
			return Poligono2D.of(listOf(p, p1, p2, p3))
		}

		fun rectanguloHorizontal(xMin: Double, xMax: Double, yMin: Double, yMax: Double): Poligono2D {
			val p0 = Punto2D.of(xMin, yMin)
			val p1 = Punto2D.of(xMax, yMin)
			val p2 = Punto2D.of(xMax, yMax)
			val p3 = Punto2D.of(xMin, yMax)
			return Poligono2D.of(listOf(p0, p1, p2, p3))
		}
	}
	
	val area: Double
		get() {
			val n = this.numeroDeVertices
			val area = (1..n - 1).map { i -> this.getDiagonal(0, i).multiplyVectorial(this.getLado(i)) }.sum()
			return area / 2
		}
	val numeroDeVertices: Int = vertices.size

	operator fun get(i: Int): Punto2D {
		val n = this.numeroDeVertices
		require(i>=0 && i<n) {"Index = |i|, size = |n|"}
		return vertices.get(i)
	}

	fun getLado(i: Int): Vector2D {
		val n = this.numeroDeVertices
		require(i>=0 && i<n) {"Index = |i|, size = |n|"}
		return Vector2D.ofPuntos(this.vertices.get(i), this.vertices.get((i + 1) % n))
	}

	fun getDiagonal(i: Int, j: Int): Vector2D {
		val n = this.numeroDeVertices
		require(i>=0 && i<n) {"Index = |i|, size = |n|"}
		require(j>=0 && i<n) {"Index = |j|, size = |n|"}
		return Vector2D.ofPuntos(this.vertices.get(i), this.vertices.get(j))
	}

	override
	fun rota(p: Punto2D, angulo: Double): Poligono2D {
		return Poligono2D.of(this.vertices.map { x -> x.rota(p, angulo) }.toList())
	}

	override
	fun traslada(v: Vector2D): Poligono2D {
		return Poligono2D.of(this.vertices.map { x -> x.traslada(v) }.toList())
	}

	override
	fun homotecia(p: Punto2D, factor: Double): Poligono2D {
		return Poligono2D.of(this.vertices.map { x -> x.homotecia(p, factor) }.toList())
	}

	override
	fun proyectaSobre(r: Recta2D): Agregado2D {
		return Agregado2D(this.vertices.map { x -> x.proyectaSobre(r) }.toMutableSet())
	}

	override
	fun simetrico(r: Recta2D): Poligono2D {
		return Poligono2D.of(this.vertices.map { x -> x.simetrico(r) }.toList())
	}

	override
	fun transform(xt: Function1<Double, Double>, yt: Function1<Double, Double>): Poligono2D {
		return Poligono2D.of(this.vertices.map { x -> x.transform(xt, yt) }.toList())
	}

	override
	fun show(v: Ventana) {
		v.g2?.draw(this.shape(Ventana.xt, Ventana.yt))
	}

	override
	fun shape(xt: Function1<Double, Double>, yt: Function1<Double, Double>): Shape {
		val t = this.transform(xt, yt)
		val n = t.numeroDeVertices
		val polygon = GeneralPath(GeneralPath.WIND_EVEN_ODD, n)
		polygon.moveTo(t[0].x, t[0].y)
		(1..n-1).forEach({ i -> polygon.lineTo(t[i].x, t[i].y) })
		polygon.closePath()
		return polygon
	}

	public override fun toString(): String {
		return this.vertices.joinToString(separator = ",", prefix = "(", postfix = ")")
	}

}