

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import kerno.process.ProcessRunner;

/**
 * <p>Test the Process runner method.</p>
 */
public class ProcessRunnerDemo {
	public static void main(String[] args) {
		// Runs a Python interpreter
		String prog = "x=int(input('?'))\\nprint(x+3)\\n";
		String cmd = "exec(\""+prog+"\")\nquit()\n";
		ProcessRunner p = new ProcessRunner("python3 -i");
		InputStream is = new ByteArrayInputStream(cmd.getBytes());
		p.streams(is,System.out,System.err);
		p.input(System.in);
	}
}