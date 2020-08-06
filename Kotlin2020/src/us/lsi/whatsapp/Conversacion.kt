package us.lsi.whatsapp

import us.lsi.tools.File
import java.time.LocalDate
import java.time.LocalTime
import us.lsi.tools.Functions.identity

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
		val palabrasHuecas: Set<String> = File.lineasFromFile("resources/palabras_huecas.txt").filter({x->x.length>0}).toSet()
		fun data(file:String): Conversacion {		
			val mensajes = File.sequenceFromFile(file)
					.filter({x->x.length>0})
					.map({m->Mensaje.parse(m)})
					.toList()
			return Conversacion(mensajes)
		}
		@JvmStatic
		fun main(args: Array<String>) {
			val m = Conversacion.data("resources/bigbangtheory_es.txt")
			println(m)
		}
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
	
	override
	fun  toString(): String {
		return this.mensajes.joinToString(separator="\n")
	}
	
	
}