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
 * Created by abarabash on 6/8/16.
 */
public class Class2 {

    // tests for day2

    private static FacebookTestUserStore facebookStore;
    private static FacebookTestUserAccount account;
    private JSONParser parser = new JSONParser();
    private List<FacebookTestUserAccount> createdAccounts = new LinkedList<FacebookTestUserAccount>();
    private String accessToken = "";


    @BeforeClass
    public static void beforeAllTests() throws IOException {
        Properties properties = getFacebookConnectionProperties();

        facebookStore = new FacebookTestUserStore(properties.getProperty("facebook.appId1"), properties.getProperty("facebook.appSecret1"));

        facebookStore.deleteAllTestUsers();

        account = facebookStore.createTestUser(true, "read_stream");
    }

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

    @Test
    public void test001getAccessToken() throws URISyntaxException, IOException {

        String appID = "293249014342744";

        String secret = "3dd724bdd321cac8a1d4183b2a87b934";

        Helper helper = new Helper();

        HttpClient client = new DefaultHttpClient();

        HttpRequestBase requestBase = helper.buildGetResource("/oauth/access_token",
                helper.buildList("grant_type", "client_credentials", "client_id", appID, "client_secret", secret));

        HttpResponse response = client.execute(requestBase);

        HttpEntity entity = response.getEntity();

        String prefix = "access_token=";

        accessToken = EntityUtils.toString(entity).substring(prefix.length());

        System.out.println("token: " + accessToken);

    }

    @Test
    public void test002() throws IOException, URISyntaxException {

        String applicationId = "1677039442544975";
        Helper helper = new Helper();
        HttpClient client = new DefaultHttpClient();

        String jsonResponse = facebookStore.post("/%s/accounts/test-users", helper.buildList("installed", "true", "permissions", "email,offline_access"), null, applicationId);

        JSONObject user = helper.parseJsonObject(jsonResponse);


    }


}
