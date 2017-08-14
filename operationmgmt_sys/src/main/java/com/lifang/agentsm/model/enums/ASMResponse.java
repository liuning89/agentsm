package com.lifang.agentsm.model.enums;

import com.lifang.model.Response;
/**
 * Author:CK
 * 创建时间：2015年5月27日
 */
public enum ASMResponse {
	/**1:success*/
	Success(new Response("success",1)),
	/**30000:操作失败，请重试*/
	Failure(new Response("操作失败，请重试",30000)),
	/**30001:用户名或密码错误。*/
	LoginFailure(new Response("用户名或密码错误。",30001)),
	/**30001:用户名或密码错误。*/
	HouseStateError(new Response("房源状态错误",30002)),
	/**30003:非运营账号不得登陆。*/
	HouseDepartError(new Response("非运营账号不得登陆",30003));
	public Response value;
	private ASMResponse(Response response) {
		this.value=response;
	}
}
