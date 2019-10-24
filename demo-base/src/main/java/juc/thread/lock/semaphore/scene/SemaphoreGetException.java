package juc.thread.lock.semaphore.scene;

/**
 * 
 * 
 * 
 * @author yuezh2   2019年10月24日 下午11:20:36
 *
 */
public class SemaphoreGetException extends RuntimeException {

	private static final long serialVersionUID = -4528280099596208630L;

	public SemaphoreGetException() {
		super();
	}

	public SemaphoreGetException(String message) {
		super(message);
	}

	public SemaphoreGetException(String message, Throwable cause) {
		super(message, cause);
	}

	public SemaphoreGetException(Throwable cause) {
		super(cause);
	}

}

