package ai.turbochain.ipex.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import ai.turbochain.ipex.entity.TradeParameter;

public interface TradeParameterMapper {
	
	@Select("SELECT * FROM trade_parameter")
	List<TradeParameter> getTradeParameter();

}
