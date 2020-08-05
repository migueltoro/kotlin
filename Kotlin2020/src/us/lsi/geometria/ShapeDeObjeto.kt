package us.lsi.geometria

import java.awt.Shape;

public interface ShapeDeObjeto {
	
	fun shape(xt:Function1<Double,Double>, yt: Function1<Double,Double>): Shape 

}