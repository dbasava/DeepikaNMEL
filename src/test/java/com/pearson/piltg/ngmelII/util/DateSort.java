package com.pearson.piltg.ngmelII.util;

import java.util.*;

public class DateSort {
public static void main (String args[])
{
	String[] date1={"20030323AM053445","20030323PM043445","20030323AM063445"};
	
	stringSort(date1);
}
	public static void stringSort(String[] arr) { 
		for(int j = 0; j < arr.length; j++) { 
		for(int i = j + 1; i < arr.length; i++) { 
		if(arr[i].compareTo(arr[j]) < 0) { 
		String t = arr[j]; 
		arr[j] = arr[i]; 
		arr[i] = t; 
		} 
		} 
		System.out.println(arr[j]); 
		} 
		} 

}

	