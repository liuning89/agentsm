package com.lifang.agentsm.utils;

import org.apache.log4j.HTMLLayout;

public class Log4JHtmlLayOut extends HTMLLayout {

	public String getContentType() {
		return "text/html;charset=utf-8";
	}
}
