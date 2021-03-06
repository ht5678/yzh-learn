package guice.demo.simple;

import com.google.inject.Guice;
import com.google.inject.Injector;

import guice.demo.base.RealBillingService;

/**
 * 
 * @author yuezh2   2016年7月13日 下午5:19:09
 *
 */
public class SimpleDemo {

	
	
	
	public static void main(String[] args) {
	    /*
	     * Guice.createInjector() takes your Modules, and returns a new Injector
	     * instance. Most applications will call this method exactly once, in their
	     * main() method.
	     */
	    Injector injector = Guice.createInjector(new SimpleBillingModule());

	    /*
	     * Now that we've got the injector, we can build objects.
	     */
	    RealBillingService billingService = injector.getInstance(RealBillingService.class);
	    
	    
	    System.out.println(billingService);
	  }
	
	
}
