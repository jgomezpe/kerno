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
package speco.object;

import speco.jxon.JXON;

/**
 * <p>Object that is configurable by using a JXON object</p>
 *
 */

public interface Configurable {
	/**
	 * JXON Tag indicating the class type of a Configurable object
	 */
	public static final String CLASS = "class"; 
    
	/**
	 * Configures the object with the information provided by the JXON object
	 * @param jxon Configuration information
	 */
	void config(JXON jxon);
    
	/**
	 * Instantiates a Configurable object (if possible) using the provided JXON configuration information
	 * @param loader Classloader used for instantiates the object
	 * @param jxon Configuration information
	 * @return A Configurable object if it can be instantiated using the JXON configuration information, <i>null</i> otherwise
	 */
	static Configurable load(ClassLoader loader, JXON jxon) {
		Configurable obj = null;
		Class<?> aClass;
		try {
			String name = jxon.string(CLASS);
			aClass = loader.loadClass(name);
			obj = (Configurable)aClass.newInstance();
			obj.config(jxon);
		} catch (Exception e) {}
		return obj;
	}

	/**
	 * Instantiates a Configurable object (if possible) using the provided JXON configuration information
	 * @param jxon Configuration information
	 * @return A Configurable object if it can be instantiated using the JXON configuration information, <i>null</i> otherwise
	 */
	static Configurable load(JXON jxon) { return load(Configurable.class.getClassLoader(), jxon); }

	/**
	 * Configures the provided Configurable object (instantiates if <i>null</i> is provided) using the provided JXON configuration information
	 * @param obj Object to be configured.
	 * @param jxon Configuration information
	 * @return A configured version of the <i>obj</i>, a new instance if <i>null</i> was provided as <i>obj</i> parameter
	 */
	static Configurable load(Configurable obj, JXON jxon) {
		if( obj != null ) obj.config(jxon);
		else obj = load(Configurable.class.getClassLoader(), jxon); 
		return obj;
	}    
}