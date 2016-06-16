package tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import helpers.FacebookTestUserAccount;
import helpers.FacebookTestUserStore;
import helpers.Helper;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.AfterClass;
import org.testng.Assert;
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
    private FacebookTestUserAccount testUser1;
    private List<FacebookTestUserAccount> createdUsers = new LinkedList<FacebookTestUserAccount>();
    private String accessToken;
    private String userId;

    private static Properties getFacebookConnectionProperties() throws IOException {

        Properties properties = new Properties();

        InputStream stream = FacebookTestUserStore.class.getClassLoader().getResourceAsStream("facebook-app.properties");

        properties.load(stream);

        stream.close();

        return properties;
    }

    @BeforeClass
    public void beforeAllTests() throws IOException {

        parser = new JsonParser();

        helper = new Helper();

        properties = getFacebookConnectionProperties();

        applicationId = properties.getProperty("facebook.appId1");
        applicationSecret = properties.getProperty("facebook.appSecret1");

        facebookStore = new FacebookTestUserStore(applicationId, applicationSecret);

        facebookStore.deleteAllTestUsers();

        createUser();

    }


    @Test
    public void test001() {

        String message = "test message";

        String response = facebookStore.post("/" + userId + "/feed",
                helper.buildList("message", message, "access_token", accessToken), null, applicationId);

        String getResponse = facebookStore.get("/" + userId + "/feed",
                helper.buildList("access_token", accessToken), null, applicationId);

        JsonElement feed = parser.parse(getResponse);

        String actualMessage = ((JsonObject) feed).get("data").getAsJsonArray().get(0).getAsJsonObject().get("message").getAsString();

        actualMessage = actualMessage + "241";

        Assert.assertEquals(actualMessage, message);

    }

    @Test
    public void test002_Change_user_Details() {

        String accountId = testUser1.id();

        List<NameValuePair> params = new LinkedList<NameValuePair>();

        String newName = "gavedName";
        String newPassword = "parssfdg2435";

        params.add(new BasicNameValuePair("name", newName));
        params.add(new BasicNameValuePair("password", newPassword));

        String result = facebookStore.post("/%s", params, null, accountId);

        assertNameChange(accountId, "actualName");

    }

    @AfterClass
    public void tearDown() {

        facebookStore.deleteAllTestUsers();
    }

    private void assertNameChange(String accountId, String expectedName) {

        List<FacebookTestUserAccount> users = facebookStore.getAllTestUsers();

        for (FacebookTestUserAccount user : users) {

            if (user.id().equals(accountId)) {
                String userdetails = user.getUserDetails();
                JsonElement details = parser.parse(userdetails);
                String actualName = details.getAsJsonObject().get("name").getAsString();
                Assert.assertEquals(actualName, expectedName);
            }
        }
    }

    private FacebookTestUserAccount createUser() {

        String jsonResponse = facebookStore.post("/%s/accounts/test-users",
                helper.buildList("installed", "true", "permissions", "read_stream,publish_actions,user_posts"), null, applicationId);

        JsonElement user = helper.parseJsonObject(jsonResponse);

        testUser1 = new FacebookTestUserAccount(facebookStore, user);

        createdUsers.add(testUser1);

        String userdetails = testUser1.getUserDetails();

        accessToken = testUser1.accessToken();

        userId = user.getAsJsonObject().get("id").getAsString();

        return testUser1;

    }


}
