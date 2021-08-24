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
package kompari;

import kerno.Service;

/**
 * <p>An order for objects</p>
 * @param <T> Type of elements to order
 *
 */
public interface Order<T> extends Comparator<T>{
	/**
	 * Determines if one elements is less, equal or greater than other.
	 * @param one First object to be compared
	 * @param two Second object to be compared
	 * @return A value less than 0 indicates that <i>one</i> is less than <i>two</i>, a value equal to 0 indicates
	 * that <i>one</i> is equal to <i>two</i> and a value greater than 0 indicates that <i>one</i> is greater than <i>two</i>
	 */
	int compare(T one, T two);    
    
	/**
	 * Determines if object <i>one</i> is equal to object <i>two</i>
	 * @param one The first object to compare
	 * @param two The second object to compare
	 * @return (one==two)
	 */
	default boolean eq(T one, T two){ return compare(one,two)==0; }    
    
	/**
	 * Determines if one elements is less, equal or greater than other.
	 * @param one First object to be compared
	 * @param two Second object to be compared
	 * @return A value less than 0 indicates that <i>one</i> is less than <i>two</i>, a value equal to 0 indicates
	 * that <i>one</i> is equal to <i>two</i> and a value greater than 0 indicates that <i>one</i> is greater than <i>two</i>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static int COMPARE(Object one, Object two) { 
		Order service = (Order)Service.get(one, Order.class);
		if(service!=null) return service.compare(one,two);
		return 1; 
	}
    
	/**
	 * Sets the order used by object/class <i>owner</i>
	 * @param owner Object/class that will use the provided Order
	 * @param order Order used by object/class <i>owner</i>
	 */
	static void set(Object owner, Order<?> order) { Service.set(owner, Order.class, order); }
}