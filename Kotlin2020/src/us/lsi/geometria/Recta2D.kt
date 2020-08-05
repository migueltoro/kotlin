package us.lsi.geometria

class Recta2D(val punto: Punto2D, val vector: Vector2D) {
	
	companion object {
		fun  ofPuntoVector(punto: Punto2D, vector: Vector2D): Recta2D {
			return Recta2D(punto, vector)
		}
		fun ofPuntos(p1: Punto2D, p2: Punto2D): Recta2D {
			return Recta2D(p1, p2.minus(p1))
		}
	}
	
	fun puntoEnRecta(lambda:Double): Punto2D {
		return this.punto + this.vector * lambda
	}
	
	
}