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
package speco.jxon;

import java.util.HashMap;

import kopii.Copier;
import kopii.Copyable;
import speco.object.Configurable;

/**
 * <p>Title: JXON</p>
 *
 * <p>Description: A JSON object that can directly store byte arrays</p>
 *
 */

public class JXON implements Configurable, Copyable{
	/**
	 * Inner HashMap for storing JXON key/values
	 */
	protected HashMap<String, Object> attributes = new HashMap<String, Object>();

	/**
	 * Determines if the object is representing JXON (<i>extended=true</i>) or pure JSON (<i>extended=false</i>) 
	 */
	protected boolean extended;

	/**
	 * Creates a JXON
	 */
	public JXON(){ this(true); }
    
	/**
	 * Creates a JXON or JSON
	 * @param extended Determines if representing JXON (<i>extended=true</i>) or pure JSON (<i>extended=false</i>) 
	 */
	public JXON(boolean extended){ this.extended = extended; }
   
	/**
	 * Determines if the provided key is a valid one (has some value associated to it in the JXON)
	 * @param key Key to be verified
	 * @return <i>true</i> If the key has some value associated to it in the JXON, <i>false</i> otherwise
	 */
	public boolean valid(String key) { return attributes.containsKey(key); }

	/**
	 * Gets the set of keys holds by the JXON
	 * @return Set of keys holds by the JXON
	 */
	public Iterable<String> keys(){ return attributes.keySet(); }
    
	/**
	 * Sets the value for a key
	 * @param key Key to be stored
	 * @param obj Object associated to the key. It must be storable
	 * @return <i>true</i> If the key can holds the object in the JXON, <i>false</i> otherwise
	 */
	public boolean set(String key, Object obj ){
		if( storable(obj) ){
			if( obj instanceof double[] ){
				double[] a = (double[])obj;
				Object[] x = new Object[a.length];
				for( int i=0; i<a.length; i++ ) x[i] = a[i];
				obj = x;
			}else if( obj instanceof int[] ){
				int[] a = (int[])obj;
				Object[] x = new Object[a.length];
				for( int i=0; i<a.length; i++ ) x[i] = a[i];
				obj = x;
			}
			attributes.put(key, obj);
			return true;
		}
		return false;
	} 
	
	/**
	 * Gets the object associated to the provided tag
	 * @param tag Tag/key to analyze
	 * @return Object associated to the provided tag, <i>null</i> otherwise
	 */
	public Object get(String tag) { return attributes.get(tag); }
    
	/**
	 * Gets the Double associated to the provided tag
	 * @param tag Tag to analyze
	 * @return Double associated to the provided tag, <i>null</i> if no Double is associated to the tag
	 */
	public Double real( String tag ){
		try{
			Object obj = get(tag);
			if( obj instanceof Double ) return (Double)obj;
			if( obj instanceof Integer ) return (double)((Integer)obj);
		}catch(Exception e){}
		return null;
	}
	
	/**
	 * Gets the double associated to the provided tag
	 * @param tag Tag to analyze
	 * @param  ifNoFound Value to return if the tag does not have associated a double
	 * @return Double associated to the provided tag, <i>ifNoFound</i> if no Double is associated to the tag
	 */
	public double real( String tag, double ifNoFound ){
		Double v = real(tag);
		return v==null?ifNoFound:v;
	}
	
	/**
	 * Gets the Integer associated to the provided tag
	 * @param tag Tag to analyze
	 * @return Integer associated to the provided tag, <i>0</i> if no Integer is associated to the tag
	 */
	public Integer integer( String tag ){ try{ return (Integer)get(tag); }catch(Exception e){ return null; } } 
	
	/**
	 * Gets the integer associated to the provided tag
	 * @param tag Tag to analyze
	 * @param  ifNoFound Value to return if the tag does not have associated an int
	 * @return int associated to the provided tag, <i>0</i> if no int is associated to the tag
	 */
	public int integer( String tag, int ifNoFound ){
		Integer v = integer(tag);
		return v==null?ifNoFound:v; 
	} 
	
	/**
	 * Gets the Boolean value associated to the provided tag
	 * @param tag Tag to analyze
	 * @return Boolean value associated to the provided tag, <i>false</i> if no Boolean value is associated to the tag
	 */
	public Boolean bool( String tag ){ try{ return (Boolean)get(tag); }catch(Exception e){ return null; } }

	/**
	 * Gets the boolean value associated to the provided tag
	 * @param tag Tag to analyze
	 * @param  ifNoFound Value to return if the tag does not have associated a boolean
	 * @return boolean value associated to the provided tag, <i>false</i> if no boolean value is associated to the tag
	 */
	public boolean bool( String tag, boolean ifNoFound ){
		Boolean v = bool(tag);
		return v==null?ifNoFound:v; 
	}

	/**
	 * Gets the byte array associated to the provided tag
	 * @param tag Tag to analyze
	 * @return Byte array associated to the provided tag, <i>null</i> if no byte array is associated to the tag
	 */
	public byte[] blob( String tag ){ try{ return (byte[])get(tag);  }catch(Exception e){ return null; } }

	/**
	 * Gets the String associated to the provided tag
	 * @param tag Tag to analyze
	 * @return String associated to the provided tag, <i>null</i> if no String is associated to the tag
	 */    
	public String string( String tag ){ try{ return (String)get(tag); }catch(Exception e){ return null; } }

	/**
	 * Gets the Object array associated to the provided tag
	 * @param tag Tag to analyze
	 * @return Object array associated to the provided tag, <i>false</i> if no Object array is associated to the tag
	 */    
	public Object[] array( String tag ){ try{ return (Object[])get(tag); }catch(Exception e){ return null; } }

	/**
	 * Gets the integer array associated to the provided tag
	 * @param tag Tag to analyze
	 * @return Integer array associated to the provided tag, <i>null</i> if no integer array is associated to the tag
	 */
	public int[] integers_array( String tag ){ 
		Object[] a = array(tag);
		int[] x = null;
		if( a!=null ){
			x = new int[a.length];
			try{ 
				for(int i=0; i<a.length; i++ )
					x[i] = (Integer)a[i]; 
			}catch(Exception e){ x = null; }
		} 
		return x;
	}
    
	/**
	 * Gets the double array associated to the provided tag
	 * @param tag Tag to analyze
	 * @return Double array associated to the provided tag, <i>null</i> if no double array is associated to the tag
	 */
	public double[] reals_array( String tag ){
		Object[] a = array(tag);
		double[] x = null;
		if( a!=null ){
			x = new double[a.length];
			try{ 
				for(int i=0; i<a.length; i++ ) 
					x[i] = (a[i] instanceof Double)?(Double)a[i]:(Integer)a[i];
			}catch(Exception e){ x = null; }
		} 
		return x;
	}

	/**
	 * Gets the JXON associated to the provided tag
	 * @param tag Tag to analyze
	 * @return JXON associated to the provided tag, <i>null</i> if no JXON is associated to the tag
	 */
	public JXON object( String tag ){ try{ return (JXON)get(tag); }catch(Exception e){ return null; } }

	/**
	 * Determines if an object can be stored by the JXON
	 * @param obj Object to analyze
	 * @return <i>true</i> If the provided object can be stored  by the JXON, <i>false</i> otherwise
	 */
	public boolean storable(Object obj){
		if( obj instanceof Object[] ){
			Object[] v = (Object[])obj;
			int i=0;
			while( i<v.length && storable(v[i]) ){ i++; }
			return i==v.length;
		}
		return ( obj == null || obj instanceof JXON || 
				(extended && obj instanceof byte[]) ||
				obj instanceof String || obj instanceof Integer || obj instanceof Double || 
				obj instanceof Boolean || obj instanceof double[] || obj instanceof int[] );
	}

	/**
	 * Removes the tag/key and its associated object
	 * @param tag  Key to be removed
	 */
	public void remove(String tag) { attributes.remove(tag); }
    
	/**
	 * Removes all keys and values from the JXON
	 */
	public void clear() { attributes.clear(); }
    
	/**
	 * Determines the number of key/value pairs maintained by the JXON
	 * @return Number of key/value pairs maintained by the JXON
	 */
	public int size() { return attributes.size(); }
    
	/**
	 * Determines if the JXON is empty or not
	 * @return <i>true</i> if the JXON is empty, <i>false</i> otherwise
	 */
	public boolean isEmpty() { return size()==0; }
    
	/**
     * Creates a clone of the Object
     * @return A clone of the Object
     */
    @Override
    public Copyable copy(){ 
    	JXON jxon = new JXON(extended);
    	jxon.attributes = Copier.apply(attributes);
    	return jxon; 
    }
    
    /**
     * Copies from the provided JXON 
     * @param jxon JXON from which the object will copy information
     */
    @Override
    public void config(JXON jxon){
    	attributes.clear();
    	extended = jxon.extended;
    	attributes = Copier.apply(jxon.attributes);
    }
}