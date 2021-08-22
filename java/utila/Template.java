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
package utila;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import speco.jxon.JXON;

/**
 * <p>Title: Template</p>
 *
 * <p>Description: Fill a template String, replacing tags with concrete values given by a dictionary</p>
 *
 */
public class Template {
    /**
     * Gets the set of escape characters for the Regex expression
     * @return set of escape characters for the Regex expression
     */
    public static String escapechars() { return "<>()[]{}\\^-=$!|?*+."; }

    /**
     * Determines if a character is a Regex escape character
     * @param c Character to be tested
     * @return <i>true</i> if the character is a Regex escape character, <i>false</i> otherwise.
     */
    public static boolean escapechar(char c) { return escapechars().indexOf(c)>=0; }

    /**
     * Gets the replacing Regex Pattern for the provide tag delimiting character
     * @param c Tag delimiting character
     * @return A replacing Regex Pattern for the provide tag delimiting character
     */
    public static Pattern get(char c) {
	String cs = (escapechar(c)?"\\\\":"")+c;
	return Pattern.compile(cs+"([^\\\\"+cs+"]|\\\\[\\\\"+cs+"])*"+cs);
    }
	
    /**
     * Obtains a String from a template by replacing the set of tags with their associated values. A tag is limited both sides by a character <i>c</i>. 
     * For example, if <i>str='lorem ·X· dolor ·haha· amet'</i>, <i>c='·'</i> and <i>dictionary={'X':'ipsum', 'haha':'sit' }</i>
     * then this method will return the string <i>lorem ipsum dolor sit amet'</i>
     * @param str Template used for generating the String
     * @param dictionary Set of pairs <i>(TAG,value)</i> used for replacing each <i>TAG</i> by its corresponding <i>value</i>
     * @return A String from a template by replacing the set of tags with their associated values. 
     */
    public static String get(String str, JXON dictionary){
	return get(str, dictionary, '·');
    }
	
    /**
     * Obtains a String from a template by replacing the set of tags with their associated values. A tag is limited both sides by a character <i>c</i>. 
     * For example, if <i>str='lorem ·X· dolor ·haha· amet'</i>, <i>c='·'</i> and <i>dictionary={'X':'ipsum', 'haha':'sit' }</i>
     * then this method will return the string <i>lorem ipsum dolor sit amet'</i>
     * @param str Template used for generating the String
     * @param dictionary Set of pairs <i>(TAG,value)</i> used for replacing each <i>TAG</i> by its corresponding <i>value</i>
     * @param c Enclosing tag character
     * @return A String from a template by replacing the set of tags with their associated values. 
     */		
    public static String get(String str, JXON dictionary, char c){
	Pattern pattern = get(c);
	Matcher matcher = pattern.matcher(str);
	int start = 0;
	if( matcher.find(start) ){
	    StringBuilder sb = new StringBuilder();
	    do{
		int nstart = matcher.start();
		String matched = matcher.group();
		String tag = matched.substring(1, matched.length()-1);
		tag = tag.replace("\\"+c, ""+c);
		sb.append(str.substring(start,nstart));
		String txt = dictionary.string(tag);
		if( txt==null ) txt = tag;
		sb.append(txt);
		start = nstart+matched.length();
	    }while( matcher.find() );
	    sb.append(str.substring(start));
	    return sb.toString();
	}else return str; 
    }
}