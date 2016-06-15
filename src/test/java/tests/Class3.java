package tests;

import com.google.gson.JsonElement;
import helpers.FacebookTestUserAccount;
import helpers.FacebookTestUserStore;
import helpers.Helper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by abarabash on 6/14/16.
 */
public class Class3 {

    private Helper helper;
    private Properties properties;
    private String applicationId;
    private String applicationSecret;
    private FacebookTestUserStore facebookStore;
    private String accessToken;
    private FacebookTestUserAccount testUser1;
    private List<FacebookTestUserAccount> createdAccount = new LinkedList<FacebookTestUserAccount>();
    private String userid;


    private static Properties getFacebookConnectionProperties() throws IOException {

        //creating object of properties class
        Properties properties = new Properties();

        //creeating a stream for file loading
        InputStream stream = FacebookTestUserStore.class.getClassLoader().getResourceAsStream("facebook-app.properties");

        //loading file as as property
        properties.load(stream);

        //closing steam
        stream.close();

        //returning value of property
        return properties;

    }

    @BeforeClass
    public void beforeAllTests() throws IOException {

        //asign the helper field
        helper = new Helper();

        //properties field asigned
        properties = getFacebookConnectionProperties();

        applicationId = properties.getProperty("facebook.appId1");
        applicationSecret = properties.getProperty("facebook.appSecret1");

        facebookStore = new FacebookTestUserStore(applicationId, applicationSecret);

        facebookStore.deleteAllTestUsers();
    }

    @Test
    public void test001GetAccessToken() throws IOException, URISyntaxException {

        getToken();

    }

    @Test
    public void test002CreateUser() {

        String jsonResponse = facebookStore.post("/%s/accounts/test-users",
                helper.buildList("installed", "true", "permissions", "read_stream,publish_actions,user_posts"), null, applicationId);

        JsonElement user = helper.parseJsonObject(jsonResponse);

        testUser1 = new FacebookTestUserAccount(facebookStore, user);

        createdAccount.add(testUser1);

        String userdetails = testUser1.getUserDetails();

        accessToken = testUser1.accessToken();

        userid = user.getAsJsonObject().get("id").toString();

    }


    public void getToken() throws URISyntaxException, IOException {

        HttpClient client = new DefaultHttpClient();

        HttpRequestBase requestBase = helper.buildGetResource("/oauth/access_token",
                helper.buildList("grant_type", "client_credentials", "client_id",
                        applicationId, "client_secret", applicationSecret));

        HttpResponse response = client.execute(requestBase);

        HttpEntity entity = response.getEntity();

        String prefix = "access_token=";

        accessToken = EntityUtils.toString(entity).substring(prefix.length());

    }

}
