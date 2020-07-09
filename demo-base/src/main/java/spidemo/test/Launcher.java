package spidemo.test;

import java.util.Iterator;
import java.util.ServiceLoader;

import spidemo.spiinterface.IOperation;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年7月9日 下午6:12:19  
 *
 */
public class Launcher {

	
	public static void main(String[] args) {
		showSpiPlugins();
	}
	
	
	private static void showSpiPlugins() {
		ServiceLoader<IOperation> operations = ServiceLoader.load(IOperation.class);
		Iterator<IOperation> iter = operations.iterator();
		while(iter.hasNext()) {
			IOperation operation = iter.next();
			System.out.println(operation.operation(6, 3));
		}
	}
	
	
}
