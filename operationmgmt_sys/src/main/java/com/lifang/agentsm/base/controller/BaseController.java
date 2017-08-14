package com.lifang.agentsm.base.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leo.jackson.JsonTool;
import com.leo.jaxrs.fault.LeoFault;
import com.lifang.agentsm.base.model.Response;
import com.lifang.agentsm.common.BusinessException;
import com.lifang.agentsm.entity.LfEmployee;
import com.lifang.agentsm.exception.BizException;
import com.lifang.agentsm.utils.Constants;
import com.lifang.agentsm.utils.StringUtil;
import com.lifang.agentsm.utils.SystemUtil;
import com.lifang.agentsoa.facade.AgentSOAServer;
import com.lifang.sso.SsoClientUtil;
import com.lifang.sso.SystemInfo;
import com.lifang.sso.entity.SsoUser;
import com.lifang.sso.entity.SsoUserPositionOrgRelate;
import com.lifang.xmemcached.MemcachedClientAdapter;

@Validated
public class BaseController {

	public static Logger logger = LoggerFactory.getLogger(BaseController.class);

	// @Autowired
	// asdfasdfasdf
	// @Qualifier("iwXmemcachedClientAdapter")
	private MemcachedClientAdapter clientAdapter;
	@Autowired
    private SsoClientUtil ssoClientUtil;
	@Autowired
	private AgentSOAServer  agentSOA;
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public Response handleIOException(Throwable e, Locale locale,
			HttpServletRequest request) {
		// this.deleteToken(request);

		Response res = new Response();

		if (e instanceof LeoFault) {
			LeoFault lf = (LeoFault) e;
			lf.setLocale(locale);
			res.setStatus(Constants.接口异常);
			res.setMessage(e.getMessage());

		} else if (e instanceof BusinessException) {
			res.setStatus(((BusinessException) e).getStatus());
			res.setMessage(((BusinessException) e).getMessage());

		} else if (e instanceof org.springframework.http.converter.HttpMessageNotReadableException) {
			e.printStackTrace();

			res.setStatus(Constants.接口异常);
			res.setMessage("json 解析错误，请检测输入请求参数");
			this.writeLog(request, e);
		} else if (e.getClass().getName().endsWith("ClientAbortException")) {
			// e.printStackTrace();

			LeoFault lf = new LeoFault(LeoFault.SYSTEM_ERROR);
			lf.setLocale(locale);
			res.setStatus(Constants.接口异常);
			res.setMessage("客户端主动断开连接");
			logger.info("客户端主动断开连接");

			// } else if (e instanceof IWSearchException) {
			// e.printStackTrace();
			//
			// res.setErrorCode(LeoFault.SYSTEM_ERROR);
			// res.setMessage("网络异常,请稍后重试");
			// logger.error("调用搜索引擎异常", e);

		} else {
			e.printStackTrace();

			res.setStatus(Constants.接口异常);
			res.setMessage("网络异常,请稍后重试");
			this.writeLog(request, e);
		}
		return res;
	}

	/**
	 * @date 2014年9月5日 下午2:37:50
	 * @author Tom
	 * @description 打印错误日志
	 */
	private void writeLog(HttpServletRequest request, Throwable e) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> enumeration = request.getHeaderNames();
		while (enumeration.hasMoreElements()) {
			String str = enumeration.nextElement().toString();
			map.put(str, request.getHeader(str));
		}
		map.put("getRequestURI", request.getRequestURI());

		logger.error("请求异常,请求头：{};;;服务器ip：{};;;客户端ip：{}", map.toString(),
				StringUtil.getLocalIP(), request.getLocalAddr(), e);

	}

	/**
	 * @date 2014年9月5日 下午2:36:26
	 * @author Tom
	 * @description 获取请求参数信息
	 */
	private String getRequestParam(HttpServletRequest request) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> parameterMap = null;
		try {
			parameterMap = objectMapper.readValue(request.getInputStream(),
					Map.class);
		} catch (Exception ex) {
			parameterMap = new HashMap<String, Object>();
		}
		parameterMap.put("getRequestURI", request.getRequestURI());

		// return JsonTool.writeValueAsString(parameterMap);
		return parameterMap.toString();
	}

	/**
	 * @date 2014年10月27日 下午7:04:39
	 * @description 将imei + UUID 做为key，系统时间做为value，加入到Memcached中，缓存30分钟
	 */
	public String getToken(String imei) {
		return imei + StringUtil.getrandomUUID();
	}

	private void deleteToken(HttpServletRequest request) {
		Object obj = request.getAttribute("token");
		if (obj != null) {
			clientAdapter.delete(String.valueOf(obj));
		}
	}

	protected void responseWrite(Object result, HttpServletRequest request,
			HttpServletResponse response) {

		Response returnObj = new Response();
		returnObj.setStatus(Constants.成功);
		returnObj.setMessage("成功");

		returnObj.setData(result);

		SystemUtil.writeResponse(request, response,
				JsonTool.writeValueAsString(returnObj));

	}

	protected void responseWrite(HttpServletRequest request,
			HttpServletResponse response) {
		responseWrite(null, request, response);
	}

	
	/**
	 * 功能描述:提取easyui表格提交过来的翻页参数
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年5月8日      新建
	 * </pre>
	 *
	 * @param pars
	 * @return
	 */
	protected void getPars(Map<String, Object> pars){
		int page = Integer.parseInt(pars.get("page")+"");
		int rows = Integer.parseInt(pars.get("rows")+"");
		pars.put("start", (page-1) * rows);
		pars.put("end", rows);
	}
	
	/**
	 * 
	* @Title: processMiniParam 
	* @Description: 提取miniui表格提交过来的翻页参数
	* @param @param pars    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	protected void processMiniParam(Map<String, Object> pars) {
        int pageIndex = 0;
        int pageSize = 0;

        try {
            pageIndex = Integer.parseInt(pars.get("pageIndex") + "");
            pageSize = Integer.parseInt(pars.get("pageSize") + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        int start = pageIndex * pageSize;
        int end =   pageSize;

        pars.put("start", start);
        pars.put("end", end);
    }
	
	protected LfEmployee getLoginEmployeeInfo(HttpSession session) {
		
//		Object object = session.getAttribute(Constants.LOGIN_SESSION);
		
		 SsoUser ssoUser =null;
		  LfEmployee employee=null;
        try {
            ssoUser = ssoClientUtil.getCurrentUser();
            List<SsoUserPositionOrgRelate> list =  ssoClientUtil.getCurrentOrg(SystemInfo.OperationManagement);
            employee = new LfEmployee();
            employee.setId(ssoUser.getId());
            employee.setCityId(list.get(0).getCityId());
            employee.setMobile(ssoUser.getMobile());
            employee.setWorkNumber(ssoUser.getWorkNumber());
            employee.setName(ssoUser.getName());
        }
        catch (Exception e) {
            logger.error("【获取登录人信息出错:】", e);
            e.printStackTrace();
        }
        if(ssoUser!=null){
            return employee;
        }else{
            return null;
        }
	}

    /**
     * 功能描述:提取 miniui 表格提交过来的翻页参数
     *
     * <pre>
     * Modify Reason:(修改原因,不需覆盖，直接追加.)
     *     bianjie:   2015年5月8日      新建
     * </pre>
     *
     * @param params
     * @return
     */
    protected void getParamsByMiniUi(Map<String, Object> params) throws BizException {
        if(params == null || params.get("pageIndex") == null || params.get("pageSize") == null)
            throw new BizException("分页参数不能为空！");
        try{
            int page = Integer.parseInt(params.get("pageIndex").toString());
            int rows = Integer.parseInt(params.get("pageSize").toString());
            params.put("start", page * rows);
            params.put("end", rows);
        } catch (NumberFormatException e){
            throw new BizException("分页参数必须为数字！");
        }
    }
}
