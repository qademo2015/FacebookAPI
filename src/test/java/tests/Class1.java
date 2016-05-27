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
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.fail;

/**
 * Created by abarabash on 5/26/16.
 */
public class Class1 {

    private static FacebookTestUserStore facebookStore1;
    private static FacebookTestUserAccount account;
    private JSONParser parser = new JSONParser();
    private List<FacebookTestUserAccount> createdAccounts = new LinkedList<FacebookTestUserAccount>();

    @org.testng.annotations.BeforeClass
    public static void beforeAllTests() throws IOException {
        Properties properties = getFacebookConnectionProperties();
        facebookStore1 = new FacebookTestUserStore(properties.getProperty("facebook.appId1"), properties.getProperty("facebook.appSecret1"));
        facebookStore1.deleteAllTestUsers();
        account = facebookStore1.createTestUser(true, "read_stream");
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
    public void test001() throws URISyntaxException, IOException {

        String appID = "1677039442544975";
        String secret = "e46786574736789bf0047196868c77f1";

        Helper helper = new Helper();

        HttpClient client = new DefaultHttpClient();

        HttpRequestBase requestBase = helper.buildGetResource("/oauth/access_token", helper.buildList("grant_type",
                "client_credentials", "client_id", appID, "client_secret", secret));

        HttpResponse response = client.execute(requestBase);


        HttpEntity entity = response.getEntity();

        String stringEntity = EntityUtils.toString(entity);


    }

    @Test
    public void test002() {

    }

    @Test
    public void test003() {

    }

    private FacebookTestUserAccount createAccount() {
        FacebookTestUserAccount account = facebookStore1.createTestUser(true, "");
        createdAccounts.add(account);
        return account;
    }

}
