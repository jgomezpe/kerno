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
package kerno.process;
import java.io.*;

/**
 * <p>A Class for Executing External Process (commands).</p>
 */
public class ProcessRunner{
	protected Process process;
 
	protected InputStream is = null;

	protected InputToOutputStream input = null;

	/**
	 * Created a External Process represented by the command sent as parameter
	 * @param command The external process (including arguments) to be executed
	 */
	public ProcessRunner( String[] command ){ 
		try { process = Runtime.getRuntime().exec(command);
		} catch (IOException e) { e.printStackTrace(); }
	}

	/**
	 * Created a External Process represented by the command sent as parameter
	 * @param commandLine The external process (including arguments) to be executed
	 */
	public ProcessRunner( String commandLine ){
		try { process = Runtime.getRuntime().exec(commandLine);
		} catch (IOException e) { e.printStackTrace(); }
	}
    
	/**
	 * Sets the associated InputStream as output stream for the process
	 * @param is Input stream
	 */
	public void input( InputStream is ) {
		if( input != null ) input.is = is;
		else input = new InputToOutputStream(this, is, process.getOutputStream());
	}

	/**
	 * Gets the process OutputStream as an InputStream
	 * @return Process output stream as an InputStream
	 */
	public InputStream output() {
		return process!=null?process.getInputStream():null;
	}
    
	/**
	 * Sets the Output Stream used by the Process to report results
	 * @param os Output Stream used by the Process to report results
	 */
	public void output(OutputStream os) {
		InputStream out = output();
		if(out!=null)  new InputToOutputStream(this, out, os);
	}

	/**
	 * Gets the process error stream as an InputStream
	 * @return Process error stream as an InputStream
	 */
	public InputStream error() {
		return process!=null?process.getErrorStream():null;
	}
    
	/**
	 * Sets the Output Stream used by the Process to report errors
	 * @param os Output Stream used by the Process to report errors
	 */
	public void error(OutputStream os) {
		InputStream out = error();
		if(out!=null)  new InputToOutputStream(this, out, os);
	}
    
	/**
	 * Sets the associated process streams
	 * @param is Input stream
	 * @param out Output Stream used by the Process to report results
	 * @param err Output Stream used by the Process to report errors
	 */
	public void streams(InputStream is, OutputStream out, OutputStream err) {
		input(is);
		output(out);
		error(err);
	}
    
	/**
	 * Determines if the process is running or not
	 * @return <i>true</i> if the process is running, <i>false</i> otherwise
	 */
	public boolean isRunning() { return process.isAlive(); }  
    
	/**
	 * Stops the process
	 */
	public void end() { process.destroy(); }    
}