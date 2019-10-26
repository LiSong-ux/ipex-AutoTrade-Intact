package ai.turbochain.ipex.entity;

/**
 * 请求目标
 * @author 未央
 * @date 2019年9月10日 下午8:23:52
 *
 */
public class Target {
	
	//协议名
	private String protocol;
	
	//域名
	private String site;
	
	//路径
	private String path;
	
	public String getProtocol() {
		return protocol;
	}
	
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Target [protocol=" + protocol + ", site=" + site + ", path=" + path + "]";
	}

}
