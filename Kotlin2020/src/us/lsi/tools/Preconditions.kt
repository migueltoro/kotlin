package us.lsi.tools

object Preconditions {
		
	/**
	 * Checks that the boolean is true. Use for validating arguments to methods.
	 * @param condition A condition
	 */
	fun checkArgument(condition: Boolean) {
		require(condition)
	}
	
	/**
	 * Checks that the boolean is true. Use for validating arguments to methods.
	 * @param message A message
	 * @param condition A condition
	 */
	fun checkArgument(condition: Boolean, message: String) {
		require(condition) {message}
	}

	/**
	 * Checks some state of the object, not dependent on the method arguments.  
	 * @param condition A condition
	 */
	fun checkState(condition: Boolean) {
		check(condition)
	}
	/**
	 * Checks some state of the object, not dependent on the method arguments. 
	 * @param message Mensaje a imprimir
	 * @param condition A condition
	 */
	fun checkState(condition: Boolean, message:String){
		check(condition) {message}
		}
	}
	
	/**
	 * Comprueba que los par�metros son no null
	 * @param <T> El tipo del elemento	
	 * @param reference Par�metros a comprobar
	 */
//	fun checkNotNull(T... reference){
//		if(Arrays.stream(reference).anyMatch(x->x == null)){
//			throw new NullPointerException(String.format("Son nulos los elementos %s",
//					IntStream.range(0, reference.length)
//					.boxed()
//					.filter(i->reference[i] == null)
//					.collect(Collectors.toList())));
//		}
//	}
	
	/**
	 * Comprueba que los par�metros son no null
	 * @param <T> El tipo del elemento	
	 * @param reference Par�metros a comprobar
	 */
	fun checkNotNull(reference: Any?){
		requireNotNull(reference)  {"Son nulos los elementos %s"}
	}
		
	/**
	 * Checks that index is a valid element index into a list, string, or array with the specified size. 
	 * An element index may range from 0 inclusive to size exclusive. 
	 * You don't pass the list, string, or array directly; you just pass its size. 
	 * @param index Un �ndice 
	 * @param size El tama�o de la lista
	 * @return Index El �ndice del elemento
	 */
	fun checkElementIndex(index: Int, size: Int){
		require(index>=0 && index<size) {"Index = |index|, size |size|"}
	}
//	require(i>=0 && i<n) {"Index = |i|, size = |n|"}
	/**
	 * Checks that index is a valid position index into a list, string, or array with the specified size. 
	 * A position index may range from 0 inclusive to size inclusive. 
	 * You don't pass the list, string, or array directly; you just pass its size. Returns index.
	 * @param index El �ndice del elemento
	 * @param size El tama�o de la lista
	 * @return Index El �ndice del elemento
	 */
	fun checkPositionIndex(index: Int, size: Int){
		require(index>=0 && index<=size){"Index = |index|, size |size|"}
	}
	
	