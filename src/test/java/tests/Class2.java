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

    // creating fields for the class

    private static FacebookTestUserStore facebookStore; //instance of FacebookTestUserStore where most of required methods are
    private static FacebookTestUserAccount account; // instance of UserAccount
    private JSONParser parser = new JSONParser(); //using this parser for parsing Jsons
    private List<FacebookTestUserAccount> createdAccounts = new LinkedList<FacebookTestUserAccount>();// saving all created UserAcconts to this List
    private String accessToken = ""; //string for keeping accessToken


    @BeforeClass // setup method which will create necessary objects and make test state clean
    public static void beforeAllTests() throws IOException {

        Properties properties = getFacebookConnectionProperties();
        //loading properties from /resources/facebook-app.properties

        facebookStore = new FacebookTestUserStore(properties.getProperty("facebook.appId1"), properties.getProperty("facebook.appSecret1"));
        //assigning new class instance to the field using property's values

        facebookStore.deleteAllTestUsers();
        //cleaning users

        account = facebookStore.createTestUser(true, "read_stream");
        // creating of the user with "read_stream" permissions
    }

    private static Properties getFacebookConnectionProperties() throws IOException {
        Properties properties = new Properties();

        // loading facebook-app.properties as a steam/
        InputStream stream = FacebookTestUserStore.class.getClassLoader().getResourceAsStream("facebook-app.properties");
        if (stream == null) {
            fail("Could not load 'facebook-app.properties");
        }
        // loading facebook-app.properties as a steam/

        properties.load(stream); //passing stream to properties
        stream.close();


        return properties;
    }

    @Test
    public void test001getAccessToken() throws URISyntaxException, IOException {

        String appID = "293249014342744";
        String secret = "3dd724bdd321cac8a1d4183b2a87b934";

        Helper helper = new Helper(); //creating object of Helper class

        HttpClient client = new DefaultHttpClient(); //creating object of HttpClient class

        HttpRequestBase requestBase = helper.buildGetResource("/oauth/access_token",
                helper.buildList("grant_type", "client_credentials", "client_id", appID, "client_secret", secret));
        //creating a request base using helper and it's buidGetResource method, where "/oauth/access_token" is a recource,
        //buildList method creates a List of NameValuePair (3 pairs of parametes for request)

        HttpResponse response = client.execute(requestBase);
        //using requestBase for request executing and saving response to object

        HttpEntity entity = response.getEntity();
        //grabbing response entity and saving to an object

        String prefix = "access_token=";
        //creting prefix as a string

        accessToken = EntityUtils.toString(entity).substring(prefix.length());
        //using entityUtils class converting entity to a string and extracting token from it using prefix

        System.out.println("token: " + accessToken);
        //printing the token

    }

    @Test
    public void test002createNewUser() throws IOException, URISyntaxException {

        String applicationId = "1677039442544975";
        Helper helper = new Helper();
        HttpClient client = new DefaultHttpClient();

        String jsonResponse = facebookStore.post("/%s/accounts/test-users", helper.buildList("installed", "true", "permissions", "email,offline_access,publish_actions"), null, applicationId);
        //using facebookStore object and it's post method, creating a test user with "email,offline_access,publish_actions" permissions
        //passing all nesserary parameters
        //saving response to a string

        JSONObject user = helper.parseJsonObject(jsonResponse);
        //parsing string using helper class method, and saving to Json object


    }


}
