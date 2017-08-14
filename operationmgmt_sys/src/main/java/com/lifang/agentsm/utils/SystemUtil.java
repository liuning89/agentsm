package com.lifang.agentsm.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class SystemUtil {

	private static Logger logger = Logger.getLogger(SystemUtil.class);

	/** 常量配置文件 */
	public final static ResourceBundle config = ResourceBundle.getBundle("config");

	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static void main(String[] args) {
		String mobile = "15001895675";
		System.out.println(encryptMobile(mobile));
	}

	/**
	 * 获取系统属性。该方法主要被velocity模版所使用。
	 * 
	 * @param key
	 *            属性key
	 * @return 系统属性值.
	 */
	public static String getSystemProperty(String key) {
		return config.getString(key);
	}

	/** 加密手机 */
	public static String encryptMobile(String mobile) {
		StringBuffer sb = new StringBuffer();
		sb.append(mobile.substring(0, 4)).append("****").append(mobile.substring(8));
		return sb.toString();
	}

	public static String transferDate(Date date) {
		return format.format(date);
	}

	/**
	 * 设置浏览器输出头
	 */
	public static void setCacheHeader(HttpServletResponse resp, long cacheTime) {
		if (cacheTime <= 0) {
			setNoCacheHeader(resp);
			return;
		}
		long now = new Date().getTime();
		resp.setDateHeader("Expires", now + cacheTime);
		resp.setDateHeader("Last-Modified", now);
		resp.setHeader("Cache-Control", "max-age=" + (cacheTime / 1000));
	}

	public static void setNoCacheHeader(HttpServletResponse resp) {
		resp.setHeader("Pragma", "No-Cache");
		resp.setHeader("Cache-Control", "no-cache, no-store");
		resp.setDateHeader("Expires", 0);
	}

	public static void writeResponse(HttpServletRequest req, HttpServletResponse resp, String content) {
		writeResponse(req, resp, content, 0, null);
	}

	public static void writeResponse(HttpServletRequest req, HttpServletResponse resp, String content, String jsoncallback) {
		writeResponse(req, resp, content, 0, jsoncallback);
	}

	/**
	 * 写ajax响应
	 * 
	 * @param content
	 */
	public static void writeResponse(HttpServletRequest req, HttpServletResponse resp, String content, long cacheTime, String jsoncallback) {
		String charset = req.getParameter("charset");

		PrintWriter writer = null;
		try {
			resp.setHeader("P3P", "CP=CAO PSA OUR");
			if (StringUtils.isNotBlank(charset)) {
				resp.setCharacterEncoding(charset);
			} else {
				resp.setCharacterEncoding("UTF-8");
			}

			resp.setContentType("text/plain");
			setCacheHeader(resp, cacheTime);
			writer = resp.getWriter();
			writer.write(content);
		} catch (IOException e) {
			logger.error("writeResponse error:", e);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	static HttpClient httpclient = new DefaultHttpClient();
	static BasicHttpContext httpContext = new BasicHttpContext();

	public static JSONObject sendRestInter(String url, Map<String, Object> pars) {
		JSONObject jobj = new JSONObject();
		if (pars != null) {
			Iterator<String> iter = pars.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				Object value = pars.get(key);
				jobj.put(key, value);
			}
		}
		return sendRest(url, jobj);
	}

	private static JSONObject sendRest(String url, JSONObject jobj) {
		String smsUrl = url;
		HttpPost httppost = new HttpPost(smsUrl);

		httppost.addHeader("App-Key", "fybao.superjia.com");
		httppost.addHeader("App-Secret", "MT0VT5EN1FAP7SGA840OBW2DUFJUAB");

		String strResult = "";

		try {
			BasicHttpEntity en = new BasicHttpEntity();
			en.setContentType("application/json");

			String str = jobj.toString();
			if (str == null) {
				str = "";
			}
			byte[] bytes = str.getBytes();

			// ByteInputStream bis = new ByteInputStream(bytes, bytes.length);
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes, 0, bytes.length);

			en.setContent(bis);
			en.setContentLength(bytes.length);
			httppost.setEntity(en);

			JSONObject sobj = null;
			HttpResponse response = httpclient.execute(httppost, httpContext);
			if (response.getStatusLine().getStatusCode() == 200) {
				sobj = new JSONObject();
				/* 读返回数据 */
				String conResult = EntityUtils.toString(response.getEntity());
				sobj = sobj.fromObject(conResult);
				strResult = sobj.toString();
			} else {
				String err = response.getStatusLine().getStatusCode() + "";
				strResult += "发送失败:" + err;
			}

			return sobj;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发布时间描述 当日：今日发布 ； 其他： XX天前发布
	 * 
	 * @param smdate
	 * @return
	 */
	public static String pubDateFormat(Date smdate) {
		Date bdate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		String formatStr = "";

		if (between_days == 0) {
			formatStr = "今日发布";
		} else {
			formatStr = between_days + "天前发布";
		}

		return formatStr;
	}

	/**
	 * 房屋楼层类型：返回 低层，中层，高层
	 * 
	 * @param floor
	 * @return
	 */
	public static String convertFloorType(int floor) {
		if (floor < 7) {
			return "低层(1F-6F)";
		} else if (floor >= 7 && floor <= 13) {
			return "中层(7F-13F)";
		} else {
			return "高层(14楼以上)";
		}
	}

	/**
	 * 转换装潢类型
	 * 
	 * @param type
	 * @return
	 */
	public static String convertDecorateType(int type) {
		switch (type) {
		case 1:
			return "毛坯";
		case 2:
			return "装修";
		case 3:
			return "普装";
		case 4:
			return "精装";
		case 5:
			return "豪装";
		default:
			return "";
		}
	}

	/**
	 * 舍去小数点后的数据
	 * 
	 * @param obj
	 * @return
	 */
	public static String missAfterPoint(String str) {
		if (str.contains(".")) {
			int len = str.lastIndexOf(".");
			return str.substring(0, len);
		} else {
			return str;
		}

	}

	public static String rentFreeDateFormat(Date smdate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);

		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);

		String formatStr = month + "月" + day + "号后可入住";

		return formatStr;
	}
}
