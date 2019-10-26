package ai.turbochain.ipex.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import ai.turbochain.ipex.entity.MarketParameter;

public interface MarketParameterMapper {
	
	@Select("SELECT * FROM market_parameter")
	List<MarketParameter> getMarketParameter();

}
