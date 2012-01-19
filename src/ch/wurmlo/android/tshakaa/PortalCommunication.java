package ch.wurmlo.android.tshakaa;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Scanner;

import org.apache.http.client.ClientProtocolException;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.protocol.BasicHttpContext;
//import org.apache.http.protocol.HttpContext;

import android.util.Log;

public class PortalCommunication {

	public static String postURL(String userId, String url, String title, String tags) throws ClientProtocolException, IOException, URISyntaxException {
		String encodedUrl = URLEncoder.encode(url);
		String encodedTitle = URLEncoder.encode(title) ;
		String encodedTags = URLEncoder.encode(tags);
		
		String getUriString = "http://portal.espen.ch/web/tshakaa/home/-/pastelink/add/"
							+ userId
							+ "?p_p_lifecycle=1&_pastelink_WAR_pastelinkportlet_url=" 
							+ encodedUrl
							+ "&_pastelink_WAR_pastelinkportlet_title="
							+ encodedTitle 
							+ "&_pastelink_WAR_pastelinkportlet_tags="
							+ encodedTags;
		
		Log.i(PortalCommunication.class.toString(), getUriString);
		return "";

//		DefaultHttpClient client = new DefaultHttpClient();
//		HttpContext context = new BasicHttpContext();
//		
//		URI getUri = new URI(getUriString);
//		HttpGet get = new HttpGet(getUri);
//		HttpResponse response = null;
//
//		Log.i(TshakaaPosterActivity.class.toString(), "Trying to post url: " + getUri.toString());
//		response = client.execute(get, context);
//		Log.i(TshakaaPosterActivity.class.toString(), "Response(" + response.getStatusLine().getStatusCode() + "): " + response.getStatusLine().getReasonPhrase());
//		HttpEntity entity = response.getEntity();
//		Log.i(TshakaaPosterActivity.class.toString(), "Converting response to post url");
//		InputStream inputStream = entity.getContent();
//		String string = convertStreamToString(inputStream, ENCODING);
//		return string;
	}
	
	@SuppressWarnings("unused")
	private static String convertStreamToString(InputStream is, String encoding) { 
		return new Scanner(is, encoding).useDelimiter("\\A").next();
	}

}


// not needed, GET is enough
//	public static String loadAuthKey() throws IOException {
//		Pattern pattern = Pattern.compile("p_auth=(.*)&");
//		Matcher matcher = pattern.matcher(PortalCommunication.getInititalJS());
//		if( matcher.find() == false) {
//			throw new IOException("Could not parse server response for auth key.");
//		}
//		String key = matcher.group(1);
//		return key;
//	}

	// not needed, GET is enough
//	private static final String JS_URL = "http://portal.espen.ch/web/tshakaa/home/-/pastelink/js/10403";
//	private static final String ENCODING = "UTF-8";
//	public static String getInititalJS() throws ClientProtocolException, IOException {
//		DefaultHttpClient client = new DefaultHttpClient();
//		HttpContext context = new BasicHttpContext();
//		HttpGet get = new HttpGet(JS_URL);
//		HttpResponse response = null;
//
//		Log.i(TshakaaPosterActivity.class.toString(), "Trying to get inital JS");
//		response = client.execute(get, context);
//		HttpEntity entity = response.getEntity();
//		Log.i(TshakaaPosterActivity.class.toString(), "Converting response to inital JS");
//		InputStream inputStream = entity.getContent();
//		String string = convertStreamToString(inputStream, ENCODING);
//		return string;
//	}