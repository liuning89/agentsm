package com.lifang.agentsm.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PagerListResponse<T> extends Response implements Serializable {

	private static final long serialVersionUID = 8104645802606822216L;

	private int total = 0;

	private List<T> rows = new ArrayList<T>();

}
