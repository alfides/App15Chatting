package example.app15chatting.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.model2.mvc.service.domain.User;

public class RestHttpClient {
	///Field
	///Constructor
	public RestHttpClient(){
	}
	///Method
	public User  getJsonUser01(String userId) throws Exception{

		HttpClient httpClient = new DefaultHttpClient();

		String url= 	"http://192.168.0.90:1050/user/json/getUser/"+userId.trim();

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse httpResponse = httpClient.execute(httpGet);

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

		String serverData = br.readLine();
		System.out.println("JSON : "+ serverData);

		if( serverData == null){
			return null;
		}

		JSONObject jsonobj = (JSONObject)JSONValue.parse(serverData);
		System.out.println("JSON Simple Object : " + jsonobj);

		User user = new User();
		user.setUserId( jsonobj.get("userId").toString() );

		return user;
	}

	public User  getJsonUser02(String userId) throws Exception{

		HttpClient httpClient = new DefaultHttpClient();

		String url= "http://192.168.0.90:1050" +"/user/json/getUser/"+userId.trim();

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse httpResponse = httpClient.execute(httpGet);

		HttpEntity httpEntity = httpResponse.getEntity();

		InputStream is = httpEntity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

		JSONObject jsonobj = (JSONObject)JSONValue.parse(br);
		System.out.println("JSON Simple Object : " + jsonobj);

		if( jsonobj == null){
			return null;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		User user = objectMapper.readValue(jsonobj.toString(), User.class);
		System.out.println(user);

		return user;
	}
}