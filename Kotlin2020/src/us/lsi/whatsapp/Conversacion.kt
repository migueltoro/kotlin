package us.lsi.whatsapp

import us.lsi.tools.File
import java.time.LocalDate
import java.time.LocalTime
import java.util.SortedMap

data class Conversacion(val mensajes: List<Mensaje>) {
	
	val usuarios: Set<String> = this.mensajes.map({m->m.usuario}).toSet()
	val mensajesPorUsuario: Map<String,List<Mensaje>> = this.mensajes.groupBy({m->m.usuario})
	val mensajesPorDiaDeSemana: Map<String,List<Mensaje>> = this.mensajes.groupBy({m->m.fecha.getDayOfWeek().name})
	val mensajesPorFecha: Map<LocalDate,List<Mensaje>> = this.mensajes.groupBy({m->m.fecha})
	val mensajesPorHora: Map<Int,List<Mensaje>> = this.mensajes.groupBy({m->m.hora.hour})
	val numeroDeMensajesPorUsuario: Map<String,Int> = this.mensajes.groupingBy({m->m.usuario}).eachCount()
	val numeroDeMensajesPorDiaDeSemana: Map<String,Int> = this.mensajes.groupingBy({m->m.fecha.getDayOfWeek().name}).eachCount()
	val numeroDeMensajesPorFecha: Map<LocalDate,Int> = this.mensajes.groupingBy({m->m.fecha}).eachCount()
	val numeroDeMensajesPorHora: Map<Int,Int> = this.mensajes.groupingBy({m->m.hora.hour}).eachCount()
	
	companion object {
		val separadores = "[- ,;.\n()?¿!¡:\"]"	
		val textoAPalabras = {t:String -> t.split(separadores.toRegex()).asSequence().filter({ !it.isEmpty() }) }
		val palabrasHuecas: Set<String> = File.lineasFromFile("recursos/palabras_huecas.txt").filter({x->x.length>0}).toSet()
		fun data(file:String): Conversacion {		
			val mensajes = File.sequenceFromFile(file)
					.filter({x->x.length>0})
					.map({m->Mensaje.parse(m)})
					.toList()
			return Conversacion(mensajes)
		}
		@JvmStatic
		fun main(args: Array<String>) {
			val m = Conversacion.data("recursos/bigbangtheory_es.txt")
//			println(m)
			print(m.palabrasCaracteristicasDeUsuario("Leonard",3).entries.asSequence()
				.sortedByDescending({e->e.value})
				.joinToString(separator="\n"))
		}
	}
	
	override
	fun  toString(): String {
		return this.mensajes.joinToString(separator="\n")
	}
	
	var _frecuenciaDePalabras: Map<String,Int>? = null
	val frecuenciaDePalabras: Map<String,Int> 
    	get() {
			if(_frecuenciaDePalabras == null){
				_frecuenciaDePalabras = this.mensajes.asSequence()
					.map({it.texto})
					.flatMap(textoAPalabras)
					.filter({p-> p.length >0 && palabrasHuecas.contains(p)})
					.groupingBy({p->p})
					.eachCount()
			}
			return _frecuenciaDePalabras?:_frecuenciaDePalabras!!
		}
	
	val numero_de_palabras: Int
		get() {
        return this.frecuenciaDePalabras.entries.map({e->e.value}).sum()
		}
	
	var _frecuenciaDePalabrasPorUsuario: Map<UsuarioPalabra,Int>? = null
	val frecuenciaDePalabrasPorUsuario: Map<UsuarioPalabra,Int> 
    	get() {
			if(_frecuenciaDePalabrasPorUsuario == null){
				_frecuenciaDePalabrasPorUsuario = this.mensajes.asSequence()
					.map({m -> Pair(m.usuario, m.texto)})
					.flatMap({e-> e.second.split(separadores.toRegex()).asSequence().map({p->UsuarioPalabra.of(e.first,p)})})
					.filter({up-> up.palabra.length >0 && palabrasHuecas.contains(up.palabra)})
					.groupingBy({p->p})
					.eachCount()
			}
			return _frecuenciaDePalabrasPorUsuario?:_frecuenciaDePalabrasPorUsuario!!
		}
	
	val numeroDePalabrasPorUsuario: Map<String,Int>
	    get() {
			return frecuenciaDePalabrasPorUsuario.entries
				.map({e->Pair(e.key.usuario,e.value)})
				.groupingBy({e->e.first})
				.fold(0) {a:Int,e:Pair<String,Int> -> a+e.second}
		}

	private var _frecuenciaDePalabrasPorRestoDeUsuarios: MutableMap<UsuarioPalabra, Int>? = null
	val frecuenciaDePalabrasPorRestoDeUsuarios: Map<UsuarioPalabra, Int>
		get() {
			if (this._frecuenciaDePalabrasPorRestoDeUsuarios == null) {
				val fpal = this.frecuenciaDePalabrasPorUsuario.entries.asSequence()           
				var d = mutableMapOf<UsuarioPalabra, Int>()
				for (e in fpal) {
					for (x in this.usuarios) {
						if (x != e.key.usuario) {
							val up = UsuarioPalabra.of(x, e.key.palabra)
							d[up] = d.getOrElse(up, {0}) + e.value
						}
					}
				}
				this._frecuenciaDePalabrasPorRestoDeUsuarios = d
			}
			return this._frecuenciaDePalabrasPorRestoDeUsuarios ?: this._frecuenciaDePalabrasPorRestoDeUsuarios!!
		}


	val numeroDePalabrasPorRestoDeUsuarios: Map<String, Int>
		get() {
			return frecuenciaDePalabrasPorRestoDeUsuarios.entries
			.map({ e -> Pair(e.key.usuario, e.value) })
			.groupingBy({ e -> e.first })
			.fold(0) { a: Int, e: Pair<String, Int> -> a + e.second }
		}
	
	fun importanciaDePalabra(usuario:String,palabra:String) : Double {
		val up = UsuarioPalabra.of(usuario,palabra)
		val npu = this.numeroDePalabrasPorUsuario.getOrElse(usuario,{1}).toDouble()
		val npru = this.numeroDePalabrasPorRestoDeUsuarios.getOrElse(usuario,{1}).toDouble()
        return (this.frecuenciaDePalabrasPorUsuario.getOrElse(up,{0}).toDouble()
                / npu) * 
                (this.frecuenciaDePalabrasPorRestoDeUsuarios.getOrElse(up,{0}).toDouble() 
                / npru)
	}
	
	fun palabrasCaracteristicasDeUsuario(usuario:String,umbral:Int): SortedMap<String,Double> {
		return this.frecuenciaDePalabrasPorUsuario.entries.asSequence()
				.filter({e-> e.key.usuario == usuario})
		        .filter({e->this.frecuenciaDePalabras.getOrElse(e.key.palabra,{0}) > umbral})
		        .filter({e->e.value > umbral})
				.filter({e->this.frecuenciaDePalabrasPorRestoDeUsuarios.getOrElse(e.key,{0}) > umbral})
				.map({e->Pair(e.key.palabra,this.importanciaDePalabra(e.key.usuario,e.key.palabra))})
				.toMap()
		        .toSortedMap()
	}
	
	
}