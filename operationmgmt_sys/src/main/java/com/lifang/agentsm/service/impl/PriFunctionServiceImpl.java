package com.lifang.agentsm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lifang.agentsm.common.Pagination;
import com.lifang.agentsm.dao.read.PriFunctionReadMapper;
import com.lifang.agentsm.dao.write.PriFunctionWriteMapper;
import com.lifang.agentsm.model.AppnameMiniuiEntity;
import com.lifang.agentsm.model.PriFunction;
import com.lifang.agentsm.model.PriUrl;
import com.lifang.agentsm.model.req.PriFunctionReq;
import com.lifang.agentsm.service.PriFunctionService;
import com.lifang.model.PageResponse;
import com.lifang.model.Response;

/**
 * 
 * 功能
 *
 * @author   Yang'ushan
 * @Date	 2015年7月22日		下午4:24:53
 *
 * @see
 */
@Service
public class PriFunctionServiceImpl implements PriFunctionService {

	@Autowired
	private PriFunctionReadMapper priFunctionReadMapper;
	
	@Autowired
	private PriFunctionWriteMapper priFunctionWriteMapper;
	
	@Override
	public Response list(PriFunctionReq req) {
		if (req.getPageIndex() != null) {
			req.setPageIndex(req.getPageIndex()+1);
		}
		Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
		PageResponse response = new PageResponse("success", 1, priFunctionReadMapper.list(pagination));
		response.setTotal(pagination.getTotal());
		return response;
	}

	@Override
	public int add(PriFunction priFunction) {
		return priFunctionWriteMapper.add(priFunction);
	}

	@Override
	public PriFunction priFunction(int id) {
		return priFunctionReadMapper.priFunction(id);
	}

	@Override
	public int update(PriFunction priFunction) {
		return priFunctionWriteMapper.update(priFunction);
	}

	@Override
	public int delete(int id) {
		return priFunctionWriteMapper.delete(id);
	}

	@Override
	public List<AppnameMiniuiEntity> appSimpleList() {
		return priFunctionReadMapper.appSimpleList();
	}

	@Override
	public List<PriUrl> urlList(int functionId, String name) {
		return priFunctionReadMapper.urlList(functionId, name);
	}

	@Override
	@Transactional
	public void saveUrls(List<PriUrl> urls, int functionId,
			List<Integer> deleteUrlIds) {
		try {
			List<PriUrl> addUrls = new ArrayList<>();
			if (urls != null && urls.size() > 0) {
				for (PriUrl url : urls) {
					if (url.getId() != null) { // 修改
						priFunctionWriteMapper.updateUrl(url);
					} else {
						addUrls.add(url);
					}
				}
			}
			if (addUrls.size() > 0) {
				priFunctionWriteMapper.addUrls(addUrls, functionId);
			}
			if (deleteUrlIds != null && deleteUrlIds.size() > 0) {
				priFunctionWriteMapper.deleteUrls(deleteUrlIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public Response getUrlFunctionList(PriFunctionReq req) {
		if (req.getPageIndex() != null) {
			req.setPageIndex(req.getPageIndex()+1);
		}
		Pagination pagination = new Pagination(req, "pageSize", "pageIndex");
		PageResponse response = new PageResponse("success", 1, priFunctionReadMapper.getUrlFunctionList(pagination));
		response.setTotal(pagination.getTotal());
		return response;
	}

	@Override
	public Integer getMenuPriFunction(String name, String appName) {
		return priFunctionReadMapper.getMenuPriFunction(name, appName);
	}


}
