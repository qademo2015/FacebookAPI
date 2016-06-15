package helpers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by abarabash on 5/27/16.
 */
public class Helper {

    private static final String FACEBOOK_HOST = "graph.facebook.com";
    private static final String ENCODING = "UTF-8";

    public HttpGet buildGetResource(String resource, List<NameValuePair> queryParams, Object... pathParams) throws URISyntaxException {
        URI uri = URIUtils.createURI("https", FACEBOOK_HOST, -1, String.format(resource, pathParams), URLEncodedUtils.format(queryParams, ENCODING), null);
        return new HttpGet(uri);
    }

    public HttpPost buildPostResource(String resource, List<NameValuePair> queryParams, List<NameValuePair> formParams, Object... pathParams) throws UnsupportedEncodingException, URISyntaxException {
        URI uri = getUri(resource, queryParams, pathParams);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, ENCODING);
        HttpPost post = new HttpPost(uri);
        post.setEntity(entity);
        return post;
    }

    private URI getUri(String resource, List<NameValuePair> queryParams, Object[] pathParams) throws URISyntaxException {
        return URIUtils.createURI("https", FACEBOOK_HOST, -1, String.format(resource, pathParams), URLEncodedUtils.format(queryParams, ENCODING), null);
    }

    public List<NameValuePair> buildList(String... queryParams) {
        List<NameValuePair> result = new LinkedList<NameValuePair>();

        if (queryParams.length % 2 == 1) {
            throw new IllegalArgumentException("There must be an even number of query parameters (key, value)");
        }

        for (int i = 0; i < queryParams.length; ) {
            result.add(new BasicNameValuePair(queryParams[i], queryParams[i + 1]));
            i += 2;
        }

        return result;
    }

    public JsonObject parseJsonObject(String json) {
        JsonParser jsonParser = new JsonParser();

        JsonObject result = null;

        try {
            result = jsonParser.parse(json).getAsJsonObject();

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return result;


    }


}
