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
package kerno.reflection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * <p>Wrap of multiple ClassLoaders as one</p>
 *
 */
public class MultiClassLoader extends ClassLoader{
	protected HashMap<ClassLoader,ClassLoader> loader = new HashMap<ClassLoader,ClassLoader>();
	
	/**
	 * Adds a class loader
	 * @param cl Class loader to add
	 */
	public void addLoader( ClassLoader cl ) { loader.put(cl,cl); }

	/**
	 * Removes a class loader
	 * @param cl Class loader to remove
	 */
	public void delLoader( ClassLoader cl ) { loader.remove(cl); }
	
	/**
	 * Removes all class loader
	 */
	public void clear() { loader.clear(); }
	
	/**
	 * Loads the class with the specified binary name. This method searches for classes in the same manner as the loadClass(String, boolean) method. It is invoked by the Java virtual machine to resolve class references. Invoking this method is equivalent to invoking loadClass(name, false).
	 * @param name The binary name of the class
	 * @return The resulting Class object
	 * @throws ClassNotFoundException If the class was not found
	 */
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException{
		ClassNotFoundException e=null;
		for(ClassLoader cl:loader.keySet()) {
			try { return cl.loadClass(name);
			}catch(ClassNotFoundException x){ e = x; }
		}
		throw e;
	}
	
	/*public Object newInstance( String className, InputStream is ) throws Exception{
		Class<?> cl = loadClass(className);
		Object obj = cl.newInstance();
		return Reader.read(obj,is);
	}*/

	/**
	 * Finds the resource with the given name. A resource is some data (images, audio, text, etc) that can be accessed by class code in a way that is independent of the location of the code. 
	 * The name of a resource is a '/'-separated path name that identifies the resource.
	 * @param name The resource name
	 * @return A URL object for reading the resource, or null if the resource could not be found or the invoker doesn't have adequate privileges to get the resource.
	 */
	@Override
	public URL getResource(String name){
		for(ClassLoader cl:loader.keySet()) {
			URL url = cl.getResource(name);
			if( url != null ) return url;
		}
		return null;
	}
	
	/**
	 * Finds all the resources with the given name. A resource is some data (images, audio, text, etc) that can be accessed by class code in a way that is independent of the location of the code.
	 * The name of a resource is a /-separated path name that identifies the resource.
	 * @param name The resource name
	 * @return An enumeration of URL objects for the resource. If no resources could be found, the enumeration will be empty. Resources that the class loader doesn't have access to will not be in the enumeration.
	 * @throws IOException If I/O errors occur
	 */
	@Override
	public Enumeration<URL> getResources(String name) throws IOException{
		IOException e=null;
		for(ClassLoader cl:loader.keySet()) {
			try {
				return cl.getResources(name);
			}catch(IOException x){ e = x; }
		}
		throw e;
	}

	/**
	 * Returns an input stream for reading the specified resource.
	 * @param name The resource name
	 * @return An input stream for reading the resource, or null if the resource could not be found
	 */
	@Override
	public InputStream getResourceAsStream(String name) {
		for(ClassLoader cl:loader.keySet()) {
			InputStream is = cl.getResourceAsStream(name);
			if( is != null ) return is;
		}
		return null;
	}	
}