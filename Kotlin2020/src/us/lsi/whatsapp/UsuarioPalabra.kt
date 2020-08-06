package us.lsi.whatsapp

data class UsuarioPalabra(val usuario: String, val palabra: String) {

	companion object {
		fun of(usuario: String, palabra: String): UsuarioPalabra {
			return UsuarioPalabra(usuario,palabra)
		}
		@JvmStatic
		fun main(args: Array<String>) {
			val m =
				Mensaje.parse("26/02/16 16:16 - Sheldon: No tiene sentido, solo creo que es una buena idea para una camiseta.")
			println(m)
		}
	}

	override
	fun toString(): String {
		return String.format("(%s,%s)", this.usuario, this.palabra)
	}
}