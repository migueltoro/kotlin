package us.lsi.coordenadas

import kotlin.math.*

data class Coordenadas2D(val latitud: Double, val longitud: Double) {	
		
	fun  toRadians() : Coordenadas2D {
		val latitud = Math.toRadians(this.latitud)
		val longitud = Math.toRadians(this.longitud)
		return Coordenadas2D(latitud, longitud)
	}
	
	fun distancia(c: Coordenadas2D): Double {
		return distancia(this,c);
	}
	
	companion object {
		@JvmStatic
		fun  of(latitud: Double, longitud: Double): Coordenadas2D {
			return Coordenadas2D(latitud, longitud);
		}		
        @JvmStatic
        fun distancia(c1:Coordenadas2D, c2:Coordenadas2D): Double {
        	val radio_tierra = 6373.0
        	val c1R = c1.toRadians()
        	val c2R = c2.toRadians()		
        	val incLat  = c2R.latitud-c1R.latitud
        	val incLong = c2R.longitud-c1R.longitud
        	val a = Math.sin(incLat/2).pow(2)+Math.cos(c1R.latitud)*Math.cos(c2R.latitud)*Math.sin(incLong/2).pow(2)
        	val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        	return radio_tierra*c;
        }
		@JvmStatic
		fun center(coordenadas: List<Coordenadas2D>): Coordenadas2D {
			val averageLat = coordenadas.map{it.latitud}.average()
			val averageLng = coordenadas.map{it.longitud}.average()
			return Coordenadas2D(averageLat,averageLng);
		}
    }

	override
	fun toString() : String {
		return String.format("(%.2f,%.2f)",this.latitud,this.longitud);
	}
	
}