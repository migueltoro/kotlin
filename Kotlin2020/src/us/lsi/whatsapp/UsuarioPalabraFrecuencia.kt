package us.lsi.whatsapp

class UsuarioPalabraFrecuencia(val usuario:String,val palabra:String, val frecuencia:Int) {
	companion object {	
		fun of(usuario:String,palabra:String,frecuencia:Int): UsuarioPalabraFrecuencia {
			return UsuarioPalabraFrecuencia(usuario,palabra,frecuencia)
		}
	}
	override
	fun toString(): String {
		return String.format("(%s,%s,%d)", this.usuario, this.palabra,this.frecuencia)
	}
}