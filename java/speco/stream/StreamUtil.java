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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * <p>Stream Utility methods</p>
 *
 */
public class StreamUtil {
	/** 
	 * Default charset used by Numtseng
	 */
	public static final String CHARSET = "UTF-8";

	/**
	 * Creates an output stream from the input stream
	 * @param is Input stream
	 * @return Output stream from the input stream
	 * @throws IOException
	 */
	protected static ByteArrayOutputStream toByteArrayOS( InputStream is ) throws IOException{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		if( is==null ) return os;
		int MAX = 1000000;
		int k=0;
		byte[] buffer = new byte[MAX];  
		do{
			k=is.read(buffer);
			if( k>0 ) os.write(buffer, 0, k);
		}while(k>0);
		os.flush();
		return os;
	}
	
	/**
	 * Writes elements read from an input stream to an output stream 
	 * @param is Input stream
	 * @param os Output stream 
	 * @return Number of bytes read from the input stream that were written to the output stream 
	 * @throws IOException If I/O occurs
	 */
	public static int write(InputStream is, OutputStream os) throws IOException {
		if( is==null ) return 0;
		int c=0;
		byte[] buffer = new byte[100000];
		int s;
		while( (s=is.read(buffer))>=0 ) {
			c += s;
			os.write(buffer, 0, s);
		}
		os.flush();
		return c;		
	}	

	/**
	 * Gets a byte array from the input stream
	 * @param is Input stream
	 * @return Byte array from the input stream
	 * @throws IOException If I/O occurs
	 */
	public static byte[] toByteArray( InputStream is ) throws IOException{ return toByteArrayOS(is).toByteArray(); }
	
	/**
	 * Reads the input stream as a String
	 * @param is Input Stream
	 * @param charsetName Character set used for decoding input stream bytes 
	 * @return String represented by the input stream
	 * @throws IOException If I/O occurs
	 */
	public static String toString( InputStream is, String charsetName ) throws IOException{ return toByteArrayOS(is).toString(charsetName); }

	/**
	 * Reads the input stream as a String using "UTF-8" decoding characters
	 * @param is Input Stream
	 * @return String represented by the input stream
	 * @throws IOException If I/O occurs
	 */
	public static String toString( InputStream is ) throws IOException{ return toString(is,CHARSET); }
	
	/**
	 * Creates an input stream from a String using the given char set for encoding characters as bytes
	 * @param str String 
	 * @param charsetName Character set used for encoding the input stream bytes
	 * @return Input stream from a String using the given char set for encoding characters as bytes
	 */
	public static InputStream toInputStream( String str, String charsetName ) { return new ByteArrayInputStream(str.getBytes(Charset.forName(charsetName))); }

	/**
	 * Creates an input stream from a String using the "UTF-8" for encoding characters as bytes
	 * @param str String 
	 * @return Input stream from a String using the "UTF-8" for encoding characters as bytes
	 */
	public static InputStream toInputStream( String str ) { return toInputStream(str,CHARSET); }

	/**
	 * Creates an input stream from a byte array/blob
	 * @param blob Byte array
	 * @return Input stream from a byte array/blob
	 */
	public static InputStream toInputStream( byte[] blob ) { return new ByteArrayInputStream(blob); }
}