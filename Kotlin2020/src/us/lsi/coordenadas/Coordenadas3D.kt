package us.lsi.coordenadas

import kotlin.math.*

data class Coordenadas3D(val latitud:Double,val longitud:Double,val altitud:Double) {
		
	fun distancia(c: Coordenadas3D):Double {
		return distancia(this,c);
	}
	
	
	fun to2D(): Coordenadas2D {
		return Coordenadas2D.of(this.latitud, this.longitud);
	}
	
	companion object {
		@JvmStatic
		fun of(latitud: Double, longitud: Double,altitud: Double): Coordenadas3D {
			return Coordenadas3D(latitud, longitud, altitud)
		}
		@JvmStatic
		fun  distancia(c1:Coordenadas3D, c2:Coordenadas3D): Double {
			val c12D = Coordenadas2D.of(c1.latitud, c1.longitud);
			val c22D = Coordenadas2D.of(c2.latitud, c2.longitud);
			return Math.sqrt(Coordenadas2D.distancia(c12D,c22D).pow(2)+(c1.altitud-c1.altitud).pow(2))
		}
	}

	override
	fun  toString(): String  {
		return String.format("(%f,%f,%f)",latitud,longitud,altitud);
	}
	
}