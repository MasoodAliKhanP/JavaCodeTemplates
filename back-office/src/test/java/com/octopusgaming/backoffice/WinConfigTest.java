package com.octopusgaming.backoffice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class WinConfigTest {

	private static Map<Integer, Integer> resultMap = new TreeMap<>();
	public static void main(String[] args) {
		System.out.println("Test");
		for(int i=0; i<10000; i++) {
			test();
		}
		
		for(Map.Entry<Integer, Integer> entry: resultMap.entrySet()) {
			System.out.println("Key: " + entry.getKey() + "  " + "Value: " + entry.getValue());
		}
		

	}
	
	private static void test() {
		int[] chanceArr = {3000, 2000, 2000, 1000, 1000, 500, 500, 33, 33};
		
		List<Integer> cumulativeList  = new ArrayList<>();
		
		int sum = 0;
		for(int chance: chanceArr) {
			sum = sum + chance;
			cumulativeList.add(sum);
		}
				
		Random r = new Random();
		int randomNumer = 1 + r.nextInt(sum);
		
		Integer i = 1;
		for(Integer cumulativeNum : cumulativeList) {
			if(randomNumer <= cumulativeNum) {
				if(resultMap.get(i) == null) {
					resultMap.put(i, 1);
					return;
				}else {
					resultMap.put(i, resultMap.get(i)+1);
					return;
				}
			}
			i++;
		}
	}

}
