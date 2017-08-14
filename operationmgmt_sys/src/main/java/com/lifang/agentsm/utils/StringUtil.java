package com.lifang.agentsm.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class StringUtil {
	
	/**
	 * @date 2014年7月9日 上午10:20:41
	 * @author Tom
	 * @description  
	 * @deprecated
	 */
	private void getLocalIP1() {
		try {
			// Linux 下不好用
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress().toString();// 获得本机IP　　
			String address = addr.getHostName().toString();// 获得本机名称
			System.out.println(ip);
			System.out.println(address);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//final Predicate<String> startsWithN = name -> name.startsWith("N");
	}
	

	public static String getrandomUUID() {
		return java.util.UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * @date 2014年7月9日 上午10:21:26
	 * @author Tom
	 * @description  
	 * 获得本机ip
	 */
	public static String getLocalIP() {
		String address = "";
		try {
			Enumeration<?> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ine = null;
			while (allNetInterfaces.hasMoreElements() && address.equals("")) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				if (!netInterface.isVirtual()) {
					Enumeration<?> addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements() && address.equals("")) {
						ine = (InetAddress) addresses.nextElement();
						if (ine != null && ine instanceof Inet4Address) {
							if (!ine.getHostAddress().equals("127.0.0.1") && !netInterface.isVirtual()) {
								address = ine.getHostAddress();
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return address.trim();
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtil.getrandomUUID());
	}
}
