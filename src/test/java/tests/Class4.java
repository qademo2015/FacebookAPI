package tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import helpers.FacebookTestUserAccount;
import helpers.FacebookTestUserStore;
import helpers.Helper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by abarabash on 6/15/16.
 */
public class Class4 {


    private JsonParser parser;
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

        //creating a stream for file loading
        InputStream stream = FacebookTestUserStore.class.getClassLoader().getResourceAsStream("facebook-app.properties");

        //loading file as property
        properties.load(stream);

        //closing stream
        stream.close();

        //returning value of property
        return properties;
    }

    @BeforeClass
    public void beforeAllTests() throws IOException {

        parser = new JsonParser();

        //asign the helper field
        helper = new Helper();
        //properties field asigned
        properties = getFacebookConnectionProperties();

        applicationId = properties.getProperty("facebook.appId1");
        applicationSecret = properties.getProperty("facebook.appSecret1");

        facebookStore = new FacebookTestUserStore(applicationId, applicationSecret);

        facebookStore.deleteAllTestUsers();

        createUser();
    }

    @Test
    public void test001_Post_Feed() {

        String message = "test message";

        String json = facebookStore.post("/" + userid + "/feed", helper.buildList("message", message, "access_token", accessToken), null, applicationId);

        String response = facebookStore.get("/" + userid + "/feed", helper.buildList("access_token", accessToken), null, applicationId);

        JsonElement feed = parser.parse(response);

        String actualMessage = ((JsonObject) feed).get("data").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString();


    }

    @Test
    public void test002_Change_User_Details() {

        String accountId = testUser1.id();

        List<NameValuePair> params = new LinkedList<NameValuePair>();

        String name = "nameChanged";
        String password = "passwordChanged";

        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("password", password));

        String result = facebookStore.post("/%s", params, null, accountId);

        assertNameChange(accountId, "nameChanged");

    }

    @AfterClass
    public void tearDown() {

        facebookStore.deleteAllTestUsers();
    }

    public FacebookTestUserAccount createUser() {

        String jsonResponse = facebookStore.post("/%s/accounts/test-users",
                helper.buildList("installed", "true", "permissions", "read_stream,publish_actions,user_posts"), null, applicationId);

        JsonElement user = helper.parseJsonObject(jsonResponse);

        testUser1 = new FacebookTestUserAccount(facebookStore, user);

        createdAccount.add(testUser1);

        String userdetails = testUser1.getUserDetails();

        accessToken = testUser1.accessToken();

        userid = user.getAsJsonObject().get("id").getAsString();

        return testUser1;
    }

    private void assertNameChange(String accountId, String name) {
        final List<FacebookTestUserAccount> users = facebookStore.getAllTestUsers();

        for (FacebookTestUserAccount user : users) {
            if (user.id().equals(accountId)) {
                String userDetails = user.getUserDetails();
                JsonElement details = parser.parse(userDetails);
                String changedName = details.getAsJsonObject().get("name").getAsString();

                Assert.assertEquals(changedName, name);
            }
        }
    }


}
