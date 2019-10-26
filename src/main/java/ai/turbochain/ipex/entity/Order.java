package ai.turbochain.ipex.entity;

/**
 * 订单参数
 * @author 未央
 * @date 2019年9月11日 上午9:36:12
 *
 */
public class Order {
	
	private String token;
	private String symbol;
	private String price;
	private String amount;
	private String direction;
	private String type;
	private String useDiscount;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUseDiscount() {
		return useDiscount;
	}
	
	public void setUseDiscount(String useDiscount) {
		this.useDiscount = useDiscount;
	}
	
	@Override
	public String toString() {
		return "Order [token=" + token + ", symbol=" + symbol + ", price=" + price + ", amount=" + amount
				+ ", direction=" + direction + ", type=" + type + ", useDiscount=" + useDiscount + "]";
	}

}
