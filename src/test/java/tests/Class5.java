package tests;

import com.google.gson.JsonParser;
import helpers.FacebookTestUserAccount;
import helpers.FacebookTestUserStore;
import helpers.Helper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by abarabash on 6/21/16.
 */
public class Class5 {


    private JsonParser parser;
    private Helper helper;
    private Properties properties;
    private String applicationId;
    private String applicationSecret;
    private FacebookTestUserStore facebookStore;
    private List<FacebookTestUserAccount> createdUsers;
    private FacebookTestUserAccount testUser;
    private String userId;
    private String accessToken;

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

        testUser = facebookStore.createTestUser(true, "read_stream,publish_actions,user_photos");

        createdUsers = new LinkedList<FacebookTestUserAccount>();

        createdUsers.add(testUser);

        userId = testUser.id();
    }

    @Test
    public void test001_Post_image() {

        String imageUrl = "https://apdt.com/images/dogs/dog-00033.jpg";

        accessToken = testUser.accessToken();

        String response = facebookStore.post("/" + userId + "/photos",
                helper.buildList("url", imageUrl), helper.buildList("access_token", accessToken), applicationId);

        String getRequestResult = facebookStore.get("/" + userId + "/photos",
                helper.buildList("access_token", accessToken), null, applicationId);


    }


}
