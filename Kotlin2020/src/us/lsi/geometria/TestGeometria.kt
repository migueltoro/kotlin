package us.lsi.geometria

import java.awt.Color
import java.util.Locale

class TestGeometria {

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			Locale.setDefault(Locale.US)
			val c1 = Circulo2D.of(Punto2D.of(0.0, 0.0), 60.0)
			val c2 = Circulo2D.of(Punto2D.of(60.0, 70.0), 30.0)
			val c3 = Circulo2D.of(Punto2D.of(-50.0, 150.0), 100.0)
			val pp0 = Punto2D.of(0.0, 0.0)
			val pp1 = Punto2D.of(50.0, 0.0)
			val pp2 = Punto2D.of(-50.0, 0.0)
			val pp3 = Punto2D.of(0.0, -50.0)
			val p1 = Punto2D.of(-50.0, -50.0)
			val p2 = Punto2D.of(50.0, -50.0)
			val p3 = Punto2D.of(50.0, 50.0)
			val p4 = Punto2D.of(-50.0, 50.0)
			val p5 = Punto2D.of(500.0, 600.0)
			val p6 = Punto2D.of(100.0, 400.0)
			val pl = Poligono2D.of(listOf(p1, p2, p3, p4))
			val pl0 = pl.rota(p2, Math.PI / 3).traslada(Vector2D.ofXY(100.0, 200.0))
			val pl2 = pl.homotecia(Punto2D.of(500.0, -500.0), 0.5)
			val r = Recta2D.ofPuntos(p5, p6)
			val pl3 = pl2.simetrico(r)
			System.out.println(pl)
			val s = Segmento2D . of (p5, p6)
			val p0 = p3.proyectaSobre(r)
			val cd = Poligono2D.cuadrado(Punto2D.of(0.0, 0.0), Vector2D.ofXY(2.0, 0.0))
			val tr = Poligono2D.trianguloEquilatero(Punto2D.of(200.0, 200.0), Vector2D.ofXY(150.0, 100.0))
			val tr2 = tr.traslada(Vector2D.ofXY(0.0, 200.0))
			System.out.println(tr)
			System.out.println(tr2)
			System.out.println(p0)
			System.out.println(p3)
			System.out.println(cd.area)
			System.out.println(Vector2D.ofXY(2.0, 1.0).proyectaSobre(Vector2D.ofXY(0.0, 2.0)).toString())
			val pc = c3.proyectaSobre(r)
			val a = Agregado2D.of(c1, c2, c3, pl, pl2, s)
			Ventana.escala = 1.0;
			Ventana.draw(a, Color.BLUE);
		}
	}
}