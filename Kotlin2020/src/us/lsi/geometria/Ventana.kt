package us.lsi.geometria

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.util.function.Function
import javax.swing.JFrame
import javax.swing.JPanel
import us.lsi.geometria.Vector2D
import us.lsi.geometria.Punto2D

data class Ventana(val objeto:ShowObjeto, val color:Color= Color.BLACK):JPanel() {
   
  var g2:Graphics2D? = null
	
  companion object {
    
	private val serialVersionUID = 865807501071430378L
    /**
	 * La anchura pantalla en pixels
	 */
    var xMax = 1200
    /**
	 * La altura pantalla en pixels
	 */
    var yMax = 700
    /**
	 * La distancia del centro de coordenadas al margen izquierdo de la ventana
	 */
    var xCentro = xMax / 2
    /**
	 * La distancia del centro de coordenadas al margen superior de la ventana
	 */
    var yCentro = yMax / 2
    /**
	 * La escala de transformación del objetos a proyectarse en la ventana
	 */
    var escala = 0.1
    val centro = Vector2D.ofXY(Ventana.xCentro.toDouble().toDouble(), Ventana.yCentro.toDouble().toDouble())
    val xt = { x: Double-> Ventana.escala * x + Ventana.xCentro } 
    val yt = { y: Double-> -Ventana.escala * y + Ventana.yCentro }
    
	fun draw(objeto:ShowObjeto, color:Color) {
      val f = JFrame("Ventana de dibujo")
      f.getContentPane().add(Ventana(objeto, color))
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
      f.setSize(Ventana.xMax, Ventana.yMax)
      f.setLocation(0, 0)
      f.setVisible(true)
    }	  
	@JvmStatic
	fun main(args:Array<String>) {
			 
    }
  }
  
  fun transform(o2d:Objeto2D):Objeto2D {
    return o2d.transform(Ventana.xt, Ventana.yt)
  }
  
  fun axes() {
    val xAxe = Segmento2D.of(Punto2D.of(-Ventana.xCentro / Ventana.escala, 0.0), Punto2D.of((Ventana.xMax - Ventana.xCentro) / Ventana.escala, 0.0))
    val yAxe = Segmento2D.of(Punto2D.of(0.0, -(Ventana.yMax - Ventana.yCentro) / Ventana.escala), Punto2D.of(0.0, Ventana.yCentro / Ventana.escala))
    xAxe.show(this)
    yAxe.show(this)
  }
	
  fun escalaAxes(g:Graphics) {
    g.drawString(String.format("%.0f", Ventana.yCentro / Ventana.escala), 2 + Ventana.xCentro, 15)
    g.drawString(String.format("%.0f", -(Ventana.yMax - Ventana.yCentro) / Ventana.escala), 2 + Ventana.xCentro,
                 Ventana.yMax - 40)
    g.drawString(String.format("%.0f", (Ventana.xMax - Ventana.xCentro) / Ventana.escala), Ventana.xMax - 60,
                 Ventana.yCentro - 2)
    g.drawString(String.format("%.0f", -Ventana.xCentro / Ventana.escala), 2, Ventana.yCentro - 2)
  }
  
  public override fun paint(g:Graphics) {
    val g2 = g as Graphics2D
    this.g2 = g2
    g.setFont(Font("Seqoe UI", Font.PLAIN, 16))
    g.setColor(Color.DARK_GRAY)
    g.drawString("Figuras Geométricas", 10, 20)
    this.axes()
    this.escalaAxes(g)
    g.setColor(this.color)
    g2.setStroke(BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL))
    this.objeto.show(this)
  }
  
}