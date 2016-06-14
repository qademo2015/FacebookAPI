package tests;

import helpers.FacebookTestUserAccount;
import helpers.FacebookTestUserStore;
import helpers.Helper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.fail;

/**
 * Created by abarabash on 6/10/16.
 */
public class Class3 {

    private static FacebookTestUserStore facebookStore;
    private static FacebookTestUserAccount account;
    private static Helper helper;
    private JSONParser parser = new JSONParser();
    private List<FacebookTestUserAccount> createdAccounts = new LinkedList<FacebookTestUserAccount>();
    private String applicationId = "";
    private String applicationSecret = "";
    private String accessToken = "";
    private String userid = "";
    private FacebookTestUserAccount testUser1;

    private static Properties getFacebookConnectionProperties() throws IOException {
        Properties properties = new Properties();
        InputStream stream = FacebookTestUserStore.class.getClassLoader().getResourceAsStream("facebook-app.properties");
        if (stream == null) {
            fail("Could not load 'facebook-app.properties");
        }
        properties.load(stream);
        stream.close();
        return properties;
    }

    @BeforeClass
    public void beforeAllTests() throws IOException {

        helper = new Helper();

        Properties properties = getFacebookConnectionProperties();

        applicationId = properties.getProperty("facebook.appId1");
        applicationSecret = properties.getProperty("facebook.appSecret1");

        facebookStore = new FacebookTestUserStore(applicationId, applicationSecret);

        facebookStore.deleteAllTestUsers();
    }

    @Test
    public void test001_Create_User() throws IOException, URISyntaxException {

        String jsonResponse = facebookStore.post("/%s/accounts/test-users", helper.buildList("installed", "true", "permissions", "read_stream,publish_actions,user_posts"), null, applicationId);

        JSONObject user = helper.parseJsonObject(jsonResponse);

        testUser1 = new FacebookTestUserAccount(facebookStore, user);

        createdAccounts.add(testUser1);

        String userDetails = testUser1.getUserDetails();

        accessToken = testUser1.accessToken();

        userid = user.get("id").toString();

    }

    @Test
    public void test002_Post_Feed() {

        String message = "test message";

        String json = facebookStore.post("/" + userid + "/feed", helper.buildList("message", "testMessage123", "access_token", accessToken), null, applicationId);

        JSONObject permissions = helper.parseJsonObject(facebookStore.get("/" + userid + "/permissions", helper.buildList("access_token", accessToken), null, applicationId));
    }

    public void getToken() throws URISyntaxException, IOException {

        HttpClient client = new DefaultHttpClient();
        HttpRequestBase requestBase = helper.buildGetResource("/oauth/access_token",
                helper.buildList("grant_type", "client_credentials", "client_id", applicationId, "client_secret", applicationSecret));
        HttpResponse response = client.execute(requestBase);
        HttpEntity entity = response.getEntity();
        String prefix = "access_token=";
        accessToken = EntityUtils.toString(entity).substring(prefix.length());
        System.out.println("token: " + accessToken);
    }


}
