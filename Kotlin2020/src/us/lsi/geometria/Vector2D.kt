package us.lsi.geometria

import us.lsi.tools.Preconditions
import kotlin.math.*
import 	java.util.Locale

data class Vector2D(val x: Double, val y: Double) {  


	val modulo: Double = Math.sqrt(this.x*this.x+this.y*this.y) 	 
	val angulo: Double = Math.atan2(this.y,this.x)	
	
	fun anguloConVector(v: Vector2D): Double {
		return Math.asin(this.multiplyVectorial(v)/(this.modulo*v.modulo));
	}
	
	fun anguloEnGrados(v: Vector2D): Double {
		return Math.toDegrees(this.anguloConVector(v))
	}
				
	companion object {
		val baseX: Vector2D = Vector2D(1.0,0.0)
		val baseY: Vector2D = Vector2D(0.0,1.0)
		fun ofXY(x:Double,y:Double): Vector2D{
			return Vector2D(x,y)
		}
		fun ofPuntos(p1:Punto2D,p2:Punto2D): Vector2D {
			return p2.minus(p1)
		}
		fun ofGrados(modulo: Double, angulo: Double): Vector2D {
			Preconditions.checkArgument(modulo > 0, String.format("El módulo debe ser mayor o igual a cero y es %.2f",modulo));
			return ofRadianes(modulo, Math.toRadians(angulo))
		}
		fun ofRadianes(modulo: Double, angulo: Double):  Vector2D {
			Preconditions.checkArgument(modulo >= 0, String.format("El módulo debe ser mayor o igual a cero y es %.2f",modulo));
			return Vector2D(modulo*Math.cos(angulo),modulo*Math.sin(angulo))		
		}
		@JvmStatic
		fun main(args:Array<String>) {
			 Locale.setDefault(Locale("en", "US"))
			 val c1 = Vector2D(2.3,4.5)
			 println(c1.modulo)
			 println(c1.angulo)
			 println(-c1)
			 println(c1.asPunto)
		}
	}
	
	private var _unitario: Vector2D? = null
	val unitario: Vector2D
	    get() {
			if(_unitario == null) _unitario = Vector2D.ofXY(this.x/this.modulo,this.y/this.modulo)
			return _unitario ?: _unitario!!
		}
	private var _ortogonal: Vector2D? = null
	val ortogonal: Vector2D
		get() {
			if(_ortogonal == null) _ortogonal = Vector2D.ofXY(-this.y, this.x)
			return _ortogonal ?: _ortogonal!!
		}
	private var _asPunto: Punto2D? = null
	val asPunto: Punto2D
		get() {
			if(_asPunto == null) _asPunto = Punto2D.of(this.y, this.x)
			return _asPunto ?: _asPunto!!
		}
	
	operator fun unaryMinus() : Vector2D = Vector2D.ofXY(-this.x, -this.y)
	
	fun proyectaSobre(v: Vector2D): Vector2D{
		val u = v.unitario
		return u * (this * u)
	}	
	
	operator fun plus(v: Vector2D): Vector2D {
		return Vector2D(this.x+v.x,this.y+v.y);
	}
	
	operator fun minus(v: Vector2D): Vector2D {
		return Vector2D(this.x-v.x,this.y-v.y);
	}
	
	fun rota(angulo: Double): Vector2D {
		return Vector2D.ofRadianes(this.modulo,this.angulo+angulo)
	}
		
	operator fun times(factor: Double): Vector2D {
		return Vector2D(this.x*factor,this.y*factor)
	}
	
	fun multiplyVectorial(v: Vector2D): Double {
		return this.x*v.y-this.y*v.x
	}
	
	operator fun times(v: Vector2D): Double {
		return this.x*v.x+this.y*v.y
	}
	
	override
	fun toString(): String  {
		return String.format("(%.2f,%.2f)",this.x, this.y);
	}
	
}