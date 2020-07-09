package spidemo.spiA;

import spidemo.spiinterface.IOperation;


/**
 * 
 * @author yuezh2
 *
 * @date 2020年7月9日 下午6:06:34  
 *
 */
public class DivisionOperationImpl implements IOperation {

	@Override
	public int operation(int numberA, int numberB) {
		// TODO Auto-generated method stub
    	System.out.println("具体实现:除法計算");
		return numberA/numberB;
	}

}
