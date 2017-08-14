package com.lifang.agentsm.dao.write;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lifang.agentsm.model.PriFunction;
import com.lifang.agentsm.model.PriUrl;

/**
 * 
 * 功能，写
 *
 * @author   Yang'ushan
 * @Date	 2015年7月24日		下午2:46:50
 *
 * @see
 */
public interface PriFunctionWriteMapper {

	/**
	 * 
	 * 添加
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param priFunction
	 * @return
	 */
	public int add(PriFunction priFunction);
	
	/**
	 * 
	 * 修改
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param priFunction
	 * @return
	 */
	public int update(PriFunction priFunction);
	
	/**
	 * 
	 * 删除
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param id
	 * @return
	 */
	public int delete(int id);
	
	/**
	 * 
	 * 添加url列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param urls
	 * @param functionId
	 * @return
	 */
	public int addUrls(@Param("urls") List<PriUrl> urls, @Param("functionId") int functionId);
	
	/**
	 * 
	 * 修改URL
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param url
	 * @return
	 */
	public int updateUrl(PriUrl url);
	
	/**
	 * 
	 * 删除URL列表
	 *
	 * <pre>
	 * Modify Reason:(修改原因,不需覆盖，直接追加.)
	 *     Yang'ushan:   2015年7月24日      新建
	 * </pre>
	 *
	 * @param urlIds
	 * @return
	 */
	public int deleteUrls(List<Integer> urlIds);
	
}
