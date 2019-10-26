package ai.turbochain.ipex.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import ai.turbochain.ipex.entity.Order;
import ai.turbochain.ipex.entity.Target;

/**
 * 查询币钻最新成交价格
 * 
 * @author 未央
 * @date 2019年9月10日 下午7:49:10
 *
 */
public class HttpUtil {

	/**
	 * 发送get请求
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String doGet(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		// 拼接字符串
		if (params != null && params.size() > 0) {
			int i = 1;
			for (Entry<String, String> entry : params.entrySet()) {
				if (i == 1) {
					url = url + "?";
				} else {
					url = url + "&";
				}
				url += entry.getKey() + "=" + entry.getValue();
				i++;
			}
		}

		// 创建http客户端
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建get请求
		HttpGet httpGet = new HttpGet(url);
		// 创建响应模型
		CloseableHttpResponse response = httpClient.execute(httpGet);
		// 从响应模型中获取响应实体
		HttpEntity responseEntity = response.getEntity();
		// 获得响应数据
		String content = null;
		if (responseEntity != null) {
			content = EntityUtils.toString(responseEntity);
		}
		// 关闭http客户端及响应模型
		httpClient.close();
		response.close();

		return content;
	}

	/**
	 * 发送post请求
	 * 
	 * @throws URISyntaxException
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String doPost(Target target, Order order) throws URISyntaxException, ClientProtocolException, IOException {
		// 创建http客户端
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		//获取参数
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("symbol", order.getSymbol()));
		params.add(new BasicNameValuePair("price", order.getPrice()));
		params.add(new BasicNameValuePair("amount", order.getAmount()));
		params.add(new BasicNameValuePair("direction", order.getDirection()));
		params.add(new BasicNameValuePair("type", order.getType()));
		params.add(new BasicNameValuePair("useDiscount", order.getUseDiscount()));
		// 创建post请求
		URI uri = new URIBuilder().setScheme(target.getProtocol()).setHost(target.getSite()).setPath(target.getPath())
				.setParameters(params).build();
		HttpPost httpPost = new HttpPost(uri);
		//设置请求头
		httpPost.setHeader("x-auth-token", order.getToken());
		// 创建响应模型
		CloseableHttpResponse response = httpClient.execute(httpPost);
		// 从响应模型中获取响应实体
		HttpEntity responseEntity = response.getEntity();
		// 获得响应数据
		String content = null;
		if (responseEntity != null) {
			content = EntityUtils.toString(responseEntity);
		}
		// 关闭http客户端及响应模型
		httpClient.close();
		response.close();

		return content;
	}

}
