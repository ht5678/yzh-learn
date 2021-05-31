package jdk8.simple;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 
 * 
 * stream:
 * 
 * 		#filter
 * 		#map
 * 		#sorted
 * 		#forEach
 * 		#collect
 * 		#mapToObj
 * 		#mapToInt
 * 		#anyMatch
 * 		#::
 * 		#->
 * 
 * IntStream:
 * 		#of
 * 		#findFirst
 * 		#ifPresent
 * 		#average
 * 
 * @author yuezh2
 * @date 2021/04/10 16:39
 *
 */
public class TestStream {
	
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");

		myList
		    .stream()
		    .filter(s -> s.startsWith("c"))
		    .map(String::toUpperCase)
		    .sorted()
		    .forEach(System.out::println);
		
		System.out.println("-------------------------------------------");
		
		//直接使用Stream.of()方法就能从一组对象创建一个stream对象，
		Stream.of("a1", "a2", "a3")
		    .findFirst()
		    .ifPresent(System.out::println);  // a
		
		System.out.println("-------------------------------------------");
		
		//JAVA 8中的IntStream,LongStream,DoubleStream这些流能够处理基本数据类型如：int,long,double。比如：IntStream可以使用range()方法能够替换掉传统的for循环
		IntStream.range(1, 4)
	    	.forEach(System.out::println);
		
		System.out.println("-------------------------------------------");
		
		//
		Arrays.stream(new int[] {1, 2, 3})
		    .map(n -> 2 * n + 1)
		    .average()
		    .ifPresent(System.out::println);  // 5.0
		
		
		System.out.println("-------------------------------------------");
		
		//
		Stream.of("d2", "a2", "b1", "b3", "c")
	    .filter(s -> {
	        System.out.println("filter: " + s);
	        return s.startsWith("a");
	    })
	    .map(s -> {
	        System.out.println("map: " + s);
	        return s.toUpperCase();
	    })
	    .forEach(s -> System.out.println("forEach: " + s));
		
		System.out.println("-------------------------------------------");
		
		
		
		
	}
	
}
