package com.lifang.agentsm.common.handler;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.log4j.Log4j;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Log4j
public class WholeExceptionHandler {
	@ExceptionHandler
	@ResponseBody
	public Map<String,Object> excptionHandler(Exception e){
		log.error("server exception:",e);
		Map<String,Object> map = new HashMap<>();
		map.put("status","20002");
		map.put("message","服务器异常");
		return map;
	}
}
