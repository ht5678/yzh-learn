package jdk8.simple;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author yuezh2
 * @date 2021/04/10 16:44
 *
 */
public class TestLambda {

	public static void main(String[] args) {
		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
		
		//1
		Collections.sort(names, new Comparator<String>() {
		    @Override
		    public int compare(String a, String b) {
		        return b.compareTo(a);
		    }
		});
		
		names.stream().forEach(System.out::println);
		System.out.println("-------------------------------------");
		
		//2
		Collections.sort(names, (String a, String b) -> {
		    return b.compareTo(a);
		});
		
		names.stream().forEach(System.out::println);
		System.out.println("-------------------------------------");
		
		//3
		Collections.sort(names, (String a, String b) -> b.compareTo(a));
		
		names.stream().forEach(System.out::println);
		System.out.println("-------------------------------------");
		
		//4
		Collections.sort(names, (a, b) -> b.compareTo(a));
		
		names.stream().forEach(System.out::println);
		System.out.println("-------------------------------------");
	}
	
	
}
