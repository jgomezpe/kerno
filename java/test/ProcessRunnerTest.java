package test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import kerno.process.ProcessRunner;

/**
 * <p>Test the Process runner method.</p>
 */
public class ProcessRunnerTest {
	public static void main(String[] args) {
		// Runs a Python interpreter
		String prog = "x=int(input('?'))\\nprint(x+3)\\n";
		String cmd = "exec(\""+prog+"\")\nquit()\n";
		System.out.println(cmd);
		ProcessRunner p = new ProcessRunner("python3 -i");
		InputStream is = new ByteArrayInputStream(cmd.getBytes());
		p.streams(is,System.out,System.err);
		try {
			// Allowing the process to be loaded (may require some time more than 1 second sometimes
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		p.input(System.in);
	}
}