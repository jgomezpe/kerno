package kerno.process;

/**
 * Process having a method for determining if it is running or not
 */
public interface CheckeableProcess {
    /**
     * Determines if the process is running or not
     * @return <i>true</i> if the process is running, <i>false</i> otherwise
     */
    boolean isRunning();
}
