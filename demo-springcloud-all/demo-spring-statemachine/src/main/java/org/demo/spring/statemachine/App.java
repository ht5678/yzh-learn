package org.demo.spring.statemachine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年6月16日 下午4:28:04  
 *
 */
@SpringBootApplication
public class App implements CommandLineRunner{
	
	
	@Autowired
	private StateMachine<States, Events> stateMachine;
	
	
	
    public static void main( String[] args ){
        SpringApplication.run(App.class, args);
    }

    
    
	@Override
	public void run(String... args) throws Exception {
		stateMachine.sendEvent(Events.E1);
		stateMachine.sendEvent(Events.E2);
	}

    
    
}
