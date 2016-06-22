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

        createdUsers = new LinkedList<FacebookTestUserAccount>();

        createdUsers.add(testUser1);

        userId = testUser1.id();


    }

    @Test
    public void test001_Post_image() {

        String imageUrl = "https://apdt.com/images/dogs/dog-00033.jpg";


        accessToken = testUser1.accessToken();

        String response = facebookStore.post("/" + userId + "/photos",
                helper.buildList("url", imageUrl), helper.buildList("access_token", accessToken), applicationId);

        String getResponse = facebookStore.get("/" + userId + "/feed",
                helper.buildList("access_token", accessToken), null, applicationId);


    }

    @Test
    public void test002_Create_album() {


        accessToken = testUser1.accessToken();

        String albumName = "NewTestAlbum";

        String response = facebookStore.post("/" + userId + "/albums",
                helper.buildList("name", albumName), helper.buildList("access_token", accessToken), applicationId);

        String getResponse = facebookStore.get("/" + userId + "/albums",
                helper.buildList("access_token", accessToken), null, applicationId);


    }

    private FacebookTestUserAccount createUser() {

        String jsonResponse = facebookStore.post("/%s/accounts/test-users",
                helper.buildList("installed", "true", "permissions", "read_stream,publish_actions,user_photos"), null, applicationId);

        JsonElement user = helper.parseJsonObject(jsonResponse);

        testUser1 = new FacebookTestUserAccount(facebookStore, user);


        String userdetails = testUser1.getUserDetails();

        accessToken = testUser1.accessToken();

        userId = user.getAsJsonObject().get("id").getAsString();

        return testUser1;

    }

}
