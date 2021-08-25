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
package speco.stream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import kerno.reflection.Reflection;

/**
 * <p>InputStream loading utilities</p>
 *
 */
public class InputStreamLoader {
	protected static IOException exception(String file) {
		return new IOException("·No such resource·" + file );
	}
	
	/**
	 * Loads an InputStream from  URL 
	 * @param url URL
	 * @return InputStream from URL
	 * @throws IOException If I/O occurs
	 */
	public static InputStream fromURL(String url) throws IOException {
		try{ return new URL(url).openStream(); }catch(IOException ex){}
		throw exception(url);
	}

	/**
	 * Loads an InputStream from file in the Operating system 
	 * @param file File identification (name)
	 * @return InputStream from file
	 * @throws IOException If I/O occurs
	 */
	public static InputStream fromOS(String file) throws IOException { 
		try{ return new FileInputStream(file); }catch(IOException ex){}
		throw exception(file);
	}
	
	/**
	 * Loads an InputStream from a class loader
	 * @param loader Class loader from which the input stream will be obtained
	 * @param file File identification (name)
	 * @return InputStream from file
	 * @throws IOException If I/O occurs
	 */
	public static InputStream fromClassLoader(ClassLoader loader, String file) throws IOException { 
	    InputStream is = loader.getResourceAsStream(file); 
	    if( is==null ) throw exception(file);
	    return is;
	}
	
	/**
	 * Loads an InputStream from the Numtseng class loader
	 * @param file File identification (name)
	 * @return InputStream from file
	 * @throws IOException If I/O occurs
	 */
	public static InputStream fromClassLoader(String file) throws IOException { 
		return fromClassLoader(Reflection.loader(), file);
	}
	
	/**
	 * Gets an InputStream searching for it as URL, as a file in the OS, or as resource in the Numtseng class loader
	 * @param file File identification (name)
	 * @return InputStream from file
	 * @throws IOException If I/O occurs
	 */
	public static InputStream get(String file)  throws IOException {
		if( file.startsWith("http://") || file.startsWith("https://") ) 
			try{ return new URL(file).openStream(); }catch(IOException ex){}
		
		try { return fromOS(file); }catch(IOException ex) {}
		
		return fromClassLoader(file);
	}
}