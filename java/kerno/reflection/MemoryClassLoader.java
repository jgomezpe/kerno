package kerno.reflection;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import speco.stream.InputStreamLoader;
import speco.stream.StreamUtil;

/**
 * <p>Class loader from a jar or zip file</p>
 *
 */
public class MemoryClassLoader extends URLClassLoader{
	/**
	 * Reads a jar file (loaded in memory, i.e. as a byte[]) as a HashMap of entries
	 * @param bytes Memory jar file
	 * @return Set of entries of the jar file (filename:byte[])
	 * @throws Exception If the jar file has some error
	 */
	public static HashMap<String, byte[]> jar(byte[] bytes) throws Exception{
		JarInputStream jar = new JarInputStream(new ByteArrayInputStream(bytes));
		final HashMap<String, byte[]> map = new HashMap<>();
		JarEntry nextEntry = null;
		while ((nextEntry = jar.getNextJarEntry()) != null) {
			final int est = (int) nextEntry.getSize();
			byte[] data = new byte[est > 0 ? est : 1024];
			int real = 0;
			for (int r = jar.read(data); r > 0; r = jar.read(data, real, data.length - real)) {
				if (data.length == (real += r)) {
					data = Arrays.copyOf(data, data.length * 2);
				}
			}
			if (real != data.length) data = Arrays.copyOf(data, real);
			map.put("/" + nextEntry.getName(), data);
		}
		jar.close();
		return map;
	} 
	
	/**
	 * Reads a zip file (loaded in memory, i.e. as a byte[]) as a HashMap of entries
	 * @param bytes Memory zip file
	 * @return Set of entries of the zip file (filename:byte[])
	 * @throws Exception If the zip file has some error
	 */
	public static HashMap<String, byte[]> zip(byte[] bytes) throws Exception{
		ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytes));
		final HashMap<String, byte[]> map = new HashMap<>();
		ZipEntry nextEntry = null;
		while ((nextEntry = zip.getNextEntry()) != null) {
			final int est = (int) nextEntry.getSize();
			byte[] data = new byte[est > 0 ? est : 1024];
			int real = 0;
			for (int r = zip.read(data); r > 0; r = zip.read(data, real, data.length - real)) {
				if (data.length == (real += r)) {
					data = Arrays.copyOf(data, data.length * 2);
				}
			}
			if (real != data.length) data = Arrays.copyOf(data, real);
			map.put("/" + nextEntry.getName(), data);
		}
		zip.close();
		return map;
	} 
	
	/**
	 * Creates a buffered URL from a set of enty:byte[] pairs
	 * @param map Set of enty:byte[] pairs
	 * @return Buffered URL from a set of enty:byte[] pairs 
	 * @throws Exception if the URL cannot be created from the map  
	 */
	public static URL url(HashMap<String, byte[]> map) throws Exception{
		return new URL("x-buffer", null, -1, "/", new URLStreamHandler() {
			@Override
			protected URLConnection openConnection(URL u) throws IOException {
				byte[] data;
				try{ data = map.get(u.getFile()); }catch(Exception e){ throw new FileNotFoundException(u.getFile()); }
				return new URLConnection(u) {
					@Override
					public void connect() throws IOException{}

					@Override
					public InputStream getInputStream() throws IOException{ return new ByteArrayInputStream(data); }
				};
			}
		});	
	}

	/**
	 * Creates a Class loader from a file (jar or zip)
	 * @param file Jar/Zip Filename 
	 * @throws Exception if the class loader cannot be created from the file
	 */
	public MemoryClassLoader( String file ) throws Exception{
		this(file, file.substring(file.length()-4).toLowerCase().equals(".jar"));
	}
	
	/**
	 * Creates a Class loader from a file (jar or zip)
	 * @param file Jar/Zip Filename 
	 * @param isJar Indicates if the file represents a jar or not
	 * @throws Exception if the class loader cannot be created from the file
	 */
	public MemoryClassLoader( String file, boolean isJar ) throws Exception{
		this(StreamUtil.toByteArray(InputStreamLoader.get(file)), isJar);
	}
	
	/**
	 * Creates a Class loader from a file (jar or zip) loaded in memory, i.e. as a byte[]
	 * @param bytes buffered Jar/Zip  
	 * @throws Exception if the class loader cannot be created from the buffered information
	 */
	public MemoryClassLoader( byte[] bytes ) throws Exception{
		this(bytes, true);
	}
	
	/**
	 * Creates a Class loader from a file (jar or zip) loaded in memory, i.e. as a byte[]
	 * @param bytes buffered Jar/Zip  
	 * @param isJar Indicates if the file represents a jar or not
	 * @throws Exception if the class loader cannot be created from the buffered information
	 */
	public MemoryClassLoader( byte[] bytes, boolean isJar ) throws Exception{
		super( new URL[]{url(isJar?jar(bytes):zip(bytes))} );
	}
}