package ch.wurmlo.android.tshakaa;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import ch.wurmlo.android.tshakaa.exceptions.TitleException;

public class Util {
	
	public static String getTitle(String url) {
		try {
		url = "http://www.google.ch/";
		URI uri = new URI(url);
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(uri);
		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		Pattern p = Pattern.compile("\\A.+<title>(.+)</title>.+\\Z");
		Matcher matcher = p.matcher(content);
		
		// does not seem to match ... 
		Log.i(Util.class.toString(), String.valueOf(matcher.matches()));
		Log.i(Util.class.toString(), matcher.group());
		Log.i(Util.class.toString(), String.valueOf(matcher.groupCount()));
		if(matcher.matches() && matcher.groupCount() == 1) {
			return matcher.group(1);
		} else {
				throw new TitleException("Title could not be extracted");
		}
		} catch (Exception e) {
			Log.e(Util.class.toString(), e.getMessage());
			e.printStackTrace();
		} finally { return ""; }
	}
	
}
