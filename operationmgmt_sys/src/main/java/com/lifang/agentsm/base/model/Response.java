/**
 * 
 */
package com.lifang.agentsm.base.model;

import lombok.Data;

@Data
public class Response {

	private int status = 0;

	private String message;

	private Object data;

	
	public Response( String message, int status,Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}


	public Response() {
	}

	
}

