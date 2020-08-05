package us.lsi.geometria

interface Objeto2D {
 
  fun rota(p:Punto2D, angulo:Double):Objeto2D
  
  fun traslada(v:Vector2D):Objeto2D
  
  fun homotecia(p:Punto2D, factor:Double):Objeto2D
  
  fun proyectaSobre(r:Recta2D):Objeto2D
  
  fun simetrico(r:Recta2D):Objeto2D
  
  fun transform(xt:Function1<Double, Double>, yt:Function1<Double, Double>):Objeto2D
	
  fun show(v: Ventana): Unit
}