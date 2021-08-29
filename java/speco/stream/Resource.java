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
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * <p>Resource utilities</p>
 *
 */
public class Resource {
	/**
	 * Gets an InputStream searching for it as URL, as a file in the OS, or as resource in the Numtseng class loader
	 * @param file File identification (name)
	 * @return InputStream from file
	 * @throws IOException If I/O occurs
	 */
	public static InputStream stream(String file)  throws IOException{
		return InputStreamLoader.get(file);
	}
	
	/**
	 * Gets an image from an input stream
	 * @param is InputStream
	 * @return Image represented by the InputStream
	 * @throws IOException If I/O occurs
	 */
	public static Image image(InputStream is ) throws IOException{ return ImageIO.read(is); }

	/**
	 * Gets an image searching for it as URL, as a file in the OS, or as resource in the Numtseng class loader
	 * @param file File identification (name)
	 * @return Image from file
	 * @throws IOException If I/O occurs
	 */
	public static Image image(String file) throws IOException{ return image(stream(file)); }
	
	/**
	 * Gets a plain text (String) from an input stream
	 * @param is InputStream
	 * @return String represented by the InputStream
	 * @throws IOException If I/O occurs
	 */
	public static String txt( InputStream is ) throws IOException{ return new String(StreamUtil.toByteArray(is)); }
	
	/**
	 * Gets a plain text file (String) searching for it as URL, as a file in the OS, or as resource in the Numtseng class loader
	 * @param file File identification (name)
	 * @return String from file
	 * @throws IOException If I/O occurs
	 */
	public static String txt( String file ) throws IOException{ return txt(stream(file)); }	
	
}