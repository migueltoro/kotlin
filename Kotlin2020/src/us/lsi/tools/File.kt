package us.lsi.tools


import java.io.File

object File {
	fun sequenceFromFile(file: String): Sequence<String>
		= File(file).bufferedReader().lineSequence()

	fun lineasFromFile(file: String): List<String> 
		=  File(file).readLines()
}