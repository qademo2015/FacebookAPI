package tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import helpers.FacebookTestUserAccount;
import helpers.FacebookTestUserStore;
import helpers.Helper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
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
    private List<FacebookTestUserAccount> createdUsers;
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

        System.out.println("s242");

    }

    private void createUser() {

        String response = facebookStore.post("%s/accounts/test_users",
                helper.buildList("installed", "true", "permissions",
                        "read_stream,publish_actions,user_posts"), null, applicationId);

        JsonElement user = helper.parseJsonObject(response);

        testUser1 = new FacebookTestUserAccount(facebookStore, user);

        createdUsers.add(testUser1);

        String userDetails = testUser1.getUserDetails();

        accessToken = testUser1.accessToken();

        userId = user.getAsJsonObject().get("id").getAsString();

    }


}
