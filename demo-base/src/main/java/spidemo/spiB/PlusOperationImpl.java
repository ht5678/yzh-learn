package spidemo.spiB;

import spidemo.spiinterface.IOperation;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年7月9日 下午6:10:58  
 *
 */
public class PlusOperationImpl implements IOperation{

	@Override
	public int operation(int numberA, int numberB) {
		// TODO Auto-generated method stub
    	System.out.println("具体实现:加法計算");
		return numberA+numberB;
	}

	
	
}
