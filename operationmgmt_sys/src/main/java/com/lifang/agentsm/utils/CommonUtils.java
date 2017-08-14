package com.lifang.agentsm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.async.DeferredResult;

import com.leo.common.util.DataUtil;
import com.lifang.agentsm.base.model.Response;
import com.lifang.agentsm.utils.EntityUtils.CheckStateEnum;

public class CommonUtils {
	public static void responseCommon(HttpServletResponse response,String contentType,String text) throws IOException{
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType);
		response.getWriter().write(text);
		response.getWriter().flush();
	}
	
	public static void responseCommon(HttpServletResponse response,String text) throws IOException{
		responseCommon(response, "text/html",text);
	}
	/**
	 * 异步输出转换
	 * @param response
	 * @return
	 */
	public static <T> DeferredResult<T> deferredResultGenic(T response) {
		DeferredResult<T> dr = new DeferredResult<T>();
		dr.setResult(response);
		return dr;
	}
	
	public static DeferredResult<Response> deferredResult(Response response) {
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(response);
		return dr;
	}
	
	public static boolean checkAndMkdirs(String path) {
		File f = new File(path);
		if (f.getParentFile() != null) {
			if ( !f.getParentFile().canWrite()) {//不可写
				return f.getParentFile().mkdirs() ;
			}else {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 轮询审核状态转换
	 * @param status
	 * @return
	 */
	public static int loopTransformStatus(int status) {
		if (status == CheckStateEnum.FAILD.getValue()) {
			return  CheckStateEnum.SUCCESS.getValue();
		}else if (status == CheckStateEnum.SUCCESS.getValue()) {
			return CheckStateEnum.FAILD.getValue();
		}else {
			return status;
		}
		
	}
	
	/** 
	 * 写文件到本地 
	 * @param in 
	 * @param fileName 
	 * @throws IOException 
	 */
	public static void copyFile(InputStream in,String path) throws FileNotFoundException,IOException{
		
		if(checkAndMkdirs(path) == false) throw new FileNotFoundException("创建文件夹失败，无法写入");
		
		FileOutputStream fs = new FileOutputStream(path);
		byte[] buffer = new byte[1024 * 1024];
		//int bytesum = 0;
		int byteread = 0;
		while ((byteread = in.read(buffer)) != -1) {
			//bytesum += byteread;
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		in.close();
	}
	
	/**
	 * 轮询审核状态转换获取文本
	 * @param status
	 * @return
	 */
	public static String loopTransformStatusDesc(int status) {
		return CheckStateEnum.getByValue(loopTransformStatus(status)).getDesc();
	}
	/**
	 * 处理房间号码
	 * 是数字就补全四位-前缀0,不是数字直接返回
	 * @param room
	 * @return
	 */
	public static String disposeHouseRoom(String room){
		if(room != null){
			boolean flag  = DataUtil.checkNum(0, 0, room);
			if(flag){
				//是纯数字
				String tmp ="000"+room;
				return tmp.substring(tmp.length()-4);
			}else{
				//不是纯数字
				return room;
			}
		}else{
			return null;
		}
	}
	/**
	 * 
	 * 把字符串转为date类型
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Andy:   2015年4月22日      新建
	 * </pre>
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date paseStr2Date(String date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date.trim());
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.err.println(disposeHouseRoom("G1"));
	}
}
