package com.lifang.agentsm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MonitorController{

	@RequestMapping("/monitor")
	@ResponseBody
	public String monitor(HttpSession session){
		return "success";
	}

}
