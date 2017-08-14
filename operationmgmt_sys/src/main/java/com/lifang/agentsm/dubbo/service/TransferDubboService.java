package com.lifang.agentsm.dubbo.service;



/**
 * 
* @ClassName: TransferService 
* @Description: TODO(资源转移service)
 * 返回true就是资源未转移.不允许终止
* @author luogq
* @date 2015年10月20日 
*
 */
public interface TransferDubboService {
	boolean transferResouce(String townid);
}
