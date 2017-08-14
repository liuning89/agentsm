package com.lifang.agentsm.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *@author 程康
 *@version 文件创建于2013-9-24 下午5:10:42
 */
public class SpringUtils implements ApplicationContextAware {
	private static ApplicationContext applicationContext;  
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}
	/**
	 * 取得存储在静态变量中的ApplicationContext. 
	 *@author 程康，时间：2013-9-24 下午5:27:44
	 * @return
	 */
	public static ApplicationContext getApplicationContext(){
		checkApplicationContext();
		return applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * 从application中获取bean
	 *@author 程康，时间：2013-9-24 下午5:45:42
	 * @param name
	 * @return
	 */
	public static <T> T getBean(String name){
		checkApplicationContext();
		return (T)applicationContext.getBean(name);
	}
	/**
	 * 从application中获取bean
	 *@author 程康，时间：2013-9-24 下午5:52:57
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz){
		checkApplicationContext();
		return (T)applicationContext.getBean(clazz);
	}
	
	
	public static void checkApplicationContext(){
		if(applicationContext == null){
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
			
		}
	}

}
