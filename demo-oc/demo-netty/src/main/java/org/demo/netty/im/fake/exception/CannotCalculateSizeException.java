package org.demo.netty.im.fake.exception;

/**
 * 
 * @author yuezh2
 * @date	  2022年3月23日 下午10:38:09
 */
public class CannotCalculateSizeException extends Exception {

    public static final long serialVersionUID = 1754096832201752388L;

    public CannotCalculateSizeException() {
    }

    public CannotCalculateSizeException(Object obj) {
        super("不能计算当前对象大小：" + obj.getClass() + ".");
    }
}