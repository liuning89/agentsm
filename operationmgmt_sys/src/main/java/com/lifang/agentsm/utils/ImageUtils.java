package com.lifang.agentsm.utils;


import org.apache.commons.lang.StringUtils;

import com.lifang.imgsoa.facade.ImageService;
import com.lifang.imgsoa.model.ImageKeyObject;

/*import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;
*/

/**
 * 文件的工具类
 * 
 */
public class ImageUtils {
	/**
	 * 功能描述:上传图片至公有桶
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年4月25日      新建
	 * </pre>
	 *
	 * @param imgClient
	 * @param img
	 * @return
	 */
	public static ImageKeyObject uploadImgToPublicBucket(ImageService imgClient,byte[] img){
		ImageKeyObject imgObj = null;
		if(img!=null)
			imgObj = imgClient.uploadSingleFileNoHandle2PubBucket(img);
		return imgObj;
	}
	
	/**
	 * 功能描述:下载公有桶图片数据
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     bianjie:   2015年4月25日      新建
	 * </pre>
	 *
	 * @param imgClient
	 * @param key
	 * @return
	 */
	public static ImageKeyObject getPublicUrlByKey(ImageService imgClient,String key){
		ImageKeyObject imgObj = null;
		if(!StringUtils.isEmpty(key))
			imgObj = imgClient.getImageKeyUrl(key);
		return imgObj;
	}
	
}
