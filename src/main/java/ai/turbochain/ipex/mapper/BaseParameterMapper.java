package ai.turbochain.ipex.mapper;

import org.apache.ibatis.annotations.Select;

import ai.turbochain.ipex.entity.BaseParameter;

public interface BaseParameterMapper {
	
	@Select("SELECT * FROM base_parameter WHERE id=(SELECT MAX(id) FROM base_parameter)")
	BaseParameter getBaseParameter();
	
//	@Insert("INSERT INTO base_parameter (cycleLength, inquireTarget, tradeTarget, priceRate, amountRate, updateTime) VALUES (#{cycleLength}, #{inquireTarget}, #{tradeTarget}, #{priceRate}, #{amountRate}, #{updateTime})")
//  int addBaseParameter(@Param("cycleLength") int cycleLength,@Param("inquireTarget") String inquireTarget,@Param("tradeTarget") String tradeTarget,@Param("priceRate") double priceRate,@Param("amountRate") double amountRate,@Param("updateTime") Date updateTime);
}
