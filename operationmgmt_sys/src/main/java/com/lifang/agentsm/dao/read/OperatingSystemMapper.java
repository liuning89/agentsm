package com.lifang.agentsm.dao.read;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.lifang.agent.model.Agent;
import com.lifang.agentsm.model.AgentAccountDetailDTO;
public interface OperatingSystemMapper {

	public List<Agent> getAgentByPhoneNumberAndCompanyId(@Param("agentPhoneNumber")String agentPhoneNumber,@Param("companyId")Integer companyId);
	
	public List<AgentAccountDetailDTO> getAgentAccountDetailDTOs(@Param("agentId")long agentId,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@Select("SELECT count(*) from lf_agent_account_details WHERE businessType = 5 AND payStatus = 1 AND createTime BETWEEN #{startTime} AND #{endTime};")
	public int agentAccountDetailDTOsByTimeCount(@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	@Select("SELECT * from lf_agent_account_details WHERE businessType = 5 AND payStatus = 1 AND createTime BETWEEN #{startTime} AND #{endTime} LIMIT #{startIndex},#{pageSize};")
	public List<AgentAccountDetailDTO> agentAccountDetailDTOsByTime(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("startIndex")int startIndex,@Param("pageSize")int pageSize);
	
	@Select("SELECT * FROM lf_agent WHERE id = #{agentId}")
	public Agent getAgentById(@Param("agentId")Integer agentId);
	
	public List<AgentAccountDetailDTO> queryAgentAccountDetatilDTOs(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("agentIds")String agentIds,@Param("rewardReson")String rewardReson,@Param("startIndex")int startIndex,@Param("pageSize")int pageSize);
	
	public int countAgentAccountDetatilDTOs(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("agentIds")String agentIds,@Param("rewardReson")String rewardReson);
	
	@Select("SELECT blance FROM cus_guest_blance WHERE guestId = #{agentId} AND paySide = 2;")
	public int getAgentBlanceByAgentId(@Param("agentId")Integer agentId);
	
	@Select("SELECT id FROM lf_agent WHERE mobile = #{agentPhoneNumber}")
	public List<Integer> getAgentIdsByMoile(@Param("agentPhoneNumber")String agentPhoneNumber);
	
	@Select("SELECT * FROM lf_agent_account_details WHERE businessType in (6,7,8,9,10) AND payStatus = 1 AND agentId in (#{agentIds}) LIMIT #{startIndex},#{pageSize}")
	public List<AgentAccountDetailDTO> queryAgentsReward(@Param("agentIds")String agentIds,@Param("startIndex")int startIndex,@Param("pageSize")int pageSize);
	
	@Select("SELECT COUNT(*) FROM lf_agent_account_details WHERE businessType in (6,7,8,9,10) AND payStatus = 1 AND agentId in (#{agentIds})")
	public int countAgentsReward(@Param("agentIds")String agentIds);
}