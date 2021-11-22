package disruptor.advanced.common;

import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;

import disruptor.simple.LogEvent;

/**
 * 
 * @author yuezh2@lenovo.com
 *	@date 2021年11月22日下午2:36:33
 */
public class EarlyReleaseHandler implements SequenceReportingEventHandler<LogEvent>{
	
	
	private Sequence sequenceCallback;
	
	private int batchRemaining = 20;
	
	
	@Override
	public void setSequenceCallback(Sequence sequenceCallback) {
		this.sequenceCallback = sequenceCallback;
	}
	
	
	

	@Override
	public void onEvent(LogEvent event, long sequence, boolean endOfBatch) throws Exception {
		processEvent(event);
		
		boolean logicalChunkofWorkComplete = isLogicalChunkOfWorkComplete();
		if(logicalChunkofWorkComplete) {
			sequenceCallback.set(sequence);
		}
		batchRemaining = logicalChunkofWorkComplete || endOfBatch ? 20 : batchRemaining;
		
	}
	
	
	/**
	 * 批处理
	 * @return
	 */
	private boolean isLogicalChunkOfWorkComplete() {
		return --batchRemaining == -1;
	}

	
	/**
	 * 
	 * @param event
	 */
	private void processEvent(final LogEvent event) {
		// do processing 
		System.out.println("cosuming msg success : "+event.toString());
	}
	
	
}
