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

import ch.wurmlo.android.tshakaa.exceptions.TitleException;

public class Util {
	
	public static String getTitle(String url) throws TitleException {
		URI uri;
		try {
			uri = new URI(url);
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(uri);
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			Pattern p = Pattern.compile("^.*?<title>(.*?)</title>.*$", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(content);
			if(matcher.find()) {
				return matcher.group(1);
			} else {
					throw new TitleException("Title could not be extracted: " + url);
			}
		} catch (URISyntaxException e) {
			throw new TitleException("URL malformed: + " + url);
		} catch (ClientProtocolException e) {
			throw new TitleException("Could not retrieve webpage: " + url);
		} catch (IOException e) {
			throw new TitleException("Could not retrieve webpage: " + url);
		}
	}
	
}
