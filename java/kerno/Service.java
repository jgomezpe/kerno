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
 * <li> Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * <li> Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <li> Neither the name of the copyright owners, their employers, nor the
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
package kerno;

import java.util.HashMap;

/**
 * <p>A service provider for objects and classes. 
 * It traverses the class hierarchy as needed.</p>
 */
public class Service{
	/**
	 * Collection of casting operations  
	 */
	protected static HashMap<Class<?>, HashMap<Object,Object>> pool = new HashMap<Class<?>, HashMap<Object,Object>>();

	/**
	 * Associates a service <i>service</i> of type <i>target</i> to object <i>caller</i> 
	 * @param caller Object that will use the service
	 * @param target Service type
	 * @param service Service
	 */
	public static void set( Object caller, Class<?> target, Object service ){
		HashMap<Object, Object> map = pool.get(target);
		if( map == null ){
			map = new HashMap<Object, Object>();
			pool.put(target, map);
		}
		map.put(caller, service);
		Class<?> cl = target.getSuperclass(); 
		if( cl != null && cl!=Object.class ) set(caller,cl, service);
		Class<?>[] superTypes = target.getInterfaces();
		for( Class<?> c:superTypes ) set(caller, c, service );
	}
	
	/**
	 * Gets the service for object <i>obj</i> of service type <i>target</i>  
	 * @param obj Object requiring the service
	 * @param target  Type of service
	 * @return Service of type <i>target</i> for object <i>obj</i>, 
	 * <i>null</i> if no service is registered.
	 */
	public static Object get( Object obj, Class<?> target ){
		HashMap<Object, Object> set = pool.get(target);
		if( set != null ){
			Object cast = set.get(obj); 
			if( cast != null ) return cast; 
			else return get( obj.getClass(), set );
		}
		return null;
	}

	/**
	 * Gets the service of type <i>target</i> for class <i>src</i>  
	 * @param src Class requesting the service
	 * @param target Collection of specific services
	 * @return Service of type <i>target</i> for class <i>src</i>, 
	 * <i>null</i> if no service is registered.
	 */
	protected static Object get( Class<?> src, HashMap<Object, Object> target ){
		if( src==null ) return null; 
		Object srv = target.get(src);
		if( srv != null ) return srv;
		srv = get( src.getSuperclass(), target);
		if( srv != null ) return srv;
		Class<?>[] superTypes = src.getInterfaces();
		for( Class<?> c:superTypes ){
			srv = get(c, target );
			if( srv != null ) return srv;
		}
		return null;
	}
	
	/** 
	 * Determines if the object is from primitive class
	 * @param obj Object to test
	 * @return <i>true</i> if the object is from a primitive class, <i>false</i> otherwise
	 */
	public static boolean primitive(Object obj) {
		return (obj == null || obj instanceof Number || obj instanceof Boolean || 
				obj instanceof Character || obj instanceof String || 
				obj.getClass().isPrimitive());
	}

	/** 
	 * Determines if the object is an array
	 * @param obj Object to test
	 * @return <i>true</i> if the object is an array, <i>false</i> otherwise
	 */		
	public static boolean array(Object obj) { return obj.getClass().isArray(); }
}