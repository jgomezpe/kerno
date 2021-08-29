package speco.stream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A Queue of InputStreams. One BufferedInputStream can be added at the end of the Queue. 
 * After adding a BufferedInputStream no more InputStreams can be added to the Queue.
 */
public class InputStreamQueue extends InputStream{
	class Node{
		InputStream is;
		Node next=null;
		Node(InputStream is){ this.is = is; }
	}
	
	protected Node head = null;
	protected Node last = null;
	
	/**
	 * Adds an InputStream to the queue
	 * @param is InputStream to add
	 */
	public void add(InputStream is) {
		if( head==null) { 
			head = new Node(is);
			last = head;
		}else if( !(last.is instanceof BufferedInputStream) ){
			Node aux = new Node(is);
			last.next = aux;
			last = aux;
		}
	}

	/**
	 * Reads from the InputStream
	 * @return Symbol read from the queue input stream, -1 if the end of the stream was reached
	 * @throws IOException if an I/O occurs
	 */
	@Override
	public int read() throws IOException {
		int c=-1;
		while(head!=null && (c = head.is.read())==-1) head = head.next;
		return c;
	}
	
	/**
	 * Returns an estimate of the number of bytes that can be read (or skipped over) from this input 
	 * stream without blocking by the next invocation of a method for this input stream. 
	 * The next invocation might be the same thread or another thread. 
	 * A single read or skip of this many bytes will not block, but may read or skip fewer bytes.
	 * @return an estimate of the number of bytes that can be read (or skipped over) from this input stream without blocking or 
	 * 0 when it reaches the end of the input stream.
	 * @throws IOException if an I/O occurs
	 */
	public int available() throws IOException {
		while(head!=null && !(head.is instanceof BufferedInputStream) && head.is.available()==0) head = head.next;
		return head!=null?head.is.available():0;
	}	
}
