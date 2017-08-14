package com.lifang.agentsm.utils;

import java.math.BigDecimal;

public class NumberTransferUtils {
	
	public static BigDecimal transferToTwoPrecision(int i){
		return transferToTwoPrecision(i,false);
	}
	
	public static BigDecimal transferToTwoPrecision(long i){
		return transferToTwoPrecision(i,false);
	}
	
	public static BigDecimal transferToTwoPrecision(int i,boolean keepPrecision){
		if (i%100 == 0 && !keepPrecision) {
			return new BigDecimal(i/100);
		}else {
			return new BigDecimal(i/100 + "." + i%100/10 + i%10);
		}
		
	}
	
	
	public static BigDecimal transferToTwoPrecision(long i,boolean keepPrecision){
		if (i%100 == 0 && !keepPrecision) {
			return new BigDecimal(i/100);
		}else {
			return new BigDecimal(i/100 + "." + i%100/10 + i%10);
		}
		
	}
	
	public static long transferTo100Long(BigDecimal b){
		if (b == null) {
			return 0;
		}
		return (long)(b.doubleValue() * 100);
		
	}
	
	public static int transferTo100Int(BigDecimal b){
		if (b == null) {
			return 0;
		}
		return (int)(b.doubleValue() * 100);
		
	}
	
	public static long transferToW(long l){
		return l/10000;
	}
	
	public static long transferWToLong(long l){
		return l * 10000;
	}
	
	@SuppressWarnings(value="unused")
	public static void main(String[] args) {
		
		BigDecimal d = new BigDecimal("1.00");
		
		int i = transferTo100Int(new BigDecimal(0));
		BigDecimal b = transferToTwoPrecision(i);
		
		int i1 = transferTo100Int(new BigDecimal(1000));
		BigDecimal b1 = transferToTwoPrecision(i1);
		
		int i2 = transferTo100Int(new BigDecimal(55));
		BigDecimal b2 = transferToTwoPrecision(i2);
		
		int ii = transferTo100Int(new BigDecimal(0.0));
		BigDecimal bb = transferToTwoPrecision(i);
		
		int i3 = transferTo100Int(new BigDecimal(55.24));
		BigDecimal b3 = transferToTwoPrecision(i3);
		
		
		int i4 = transferTo100Int(new BigDecimal(1000.09));
		BigDecimal b4 = transferToTwoPrecision(i4);
		
		int i5 = transferTo100Int(new BigDecimal(1000.00));
		BigDecimal b5 = transferToTwoPrecision(i5);
		BigDecimal b55 = transferToTwoPrecision(i5,true);
		
	}

}
