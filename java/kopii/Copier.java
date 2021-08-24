/**
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * <h3>License</h3>
 *
 * Copyright (c) 2019 by Jonatan Gomez-Perdomo. <br>
 * All rights reserved. <br>
 *
 * <p>Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <ul>
 * <li> Redistributions of source code must retain the above applyright notice,
 * this list of conditions and the following disclaimer.
 * <li> Redistributions in binary form must reproduce the above applyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <li> Neither the name of the applyright owners, their employers, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * </ul>
 * <p>THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 *
 *
 * @author <A HREF="http://disi.unal.edu.co/profesores/jgomezpe"> Jonatan Gomez-Perdomo </A>
 * (E-mail: <A HREF="mailto:jgomezpe@unal.edu.co">jgomezpe@unal.edu.co</A> )
 * @version 1.0
 */
package kopii;

import java.util.HashMap;

import kerno.Service;

/**
 * <p>A cloning method</p>
 *
 */
public class Copier {
	/**
	 * Creates a copy of an int
	 * @param obj int to be copied
	 * @return A copy of the provided int
	 */
	public static int apply(int obj) { return obj; }
    
	/**
	 * Creates a copy of an Integer
	 * @param obj Integer to be copied
	 * @return A copy of the provided Integer
	 */
	public static Integer apply(Integer obj) { return obj; }
    
	/**
	 * Creates a copy of a double
	 * @param obj double to be copied
	 * @return A copy of the provided double
	 */
	public static double apply(double obj) { return obj; }
    
	/**
	 * Creates a copy of a Double
	 * @param obj Double to be copied
	 * @return A copy of the provided Double
	 */
	public static Double apply(Double obj) { return obj; }
    
	/**
	 * Creates a copy of a boolean
	 * @param obj boolean to be copied
	 * @return A copy of the provided boolean
	 */
	public static boolean apply(boolean obj) { return obj; }
    
	/**
	 * Creates a copy of a Boolean
	 * @param obj Boolean to be copied
	 * @return A copy of the provided Boolean
	 */
	public static Boolean apply(Boolean obj) { return obj; }
    
	/**
	 * Creates a copy of a char
	 * @param obj char to be copied
	 * @return A copy of the provided char
	 */
	public static char apply(char obj) { return obj; }

	/**
	 * Creates a copy of a Character
	 * @param obj Character to be copied
	 * @return A copy of the provided Character
	 */
	public static Character apply(Character obj) { return obj; }
    
	/**
	 * Creates a copy of a long
	 * @param obj long to be copied
	 * @return A copy of the provided long
	 */
	public static long apply(long obj) { return obj; }

	/**
	 * Creates a copy of a Long
	 * @param obj Long to be copied
	 * @return A copy of the provided Long
	 */
	public static Long apply(Long obj) { return obj; }
    
	/**
	 * Creates a copy of a String
	 * @param obj String to be copied
	 * @return A copy of the provided String
	 */
	public static String apply(String obj) { return obj; }
    
	/**
	 * Creates a hard copy of a HashMap
	 * @param obj HashMap to be copied
	 * @return A hard copy of the provided HashMap
	 */
	public static HashMap<String,Object> apply(HashMap<String,Object> obj){
		HashMap<String,Object> clone = new HashMap<String, Object>();
		for( String key:obj.keySet()) clone.put(key, apply(obj.get(key)));
		return clone;
	}
   
	/**
	 * Creates a copy of a Copyable object
	 * @param obj Copyable object to be copied
	 * @return A copy of the provided Copyable object
	 */
	public static Copyable apply(Copyable obj) { return obj.copy(); }
    
	/**
	 * Creates a copy of an object
	 * @param obj Object to be copied
	 * @return A copy of the provided object
	 */
	@SuppressWarnings("unchecked")
	public static Object apply(Object obj) {
		if(obj==null) return null;
		if(Service.array(obj)) {
			int n = java.lang.reflect.Array.getLength(obj);
			Object clone = java.lang.reflect.Array.newInstance(Object.class,n);
			for( int i=0; i<n; i++)
				java.lang.reflect.Array.set(clone, i, 
						Copier.apply(java.lang.reflect.Array.get(obj, i)));
			return clone;
		}
		if(obj instanceof Copyable) return apply((Copyable)obj);
		if(obj instanceof HashMap) return apply((HashMap<String,Object>)obj);
		return obj; 
	}    
}