package tests;

import com.google.gson.JsonElement;
import helpers.FacebookTestUserAccount;
import helpers.FacebookTestUserStore;
import helpers.Helper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by abarabash on 6/22/16.
 */
public class Class6 {


    private FacebookTestUserStore facebookStore;
    private String applicationID;
    private String aplicationSecret;
    private Helper helper;
    private FacebookTestUserAccount testUser1;
    private FacebookTestUserAccount testUser2;
    private String accessToken;
    private String userId;

    public static Properties getFacebookConnectionProperties() throws IOException {

        Properties properties = new Properties();

        InputStream stream = FacebookTestUserStore.class.getClassLoader().getResourceAsStream("facebook-app.properties");

        properties.load(stream);

        stream.close();

        return properties;
    }

    @BeforeClass
    public void beforeAllTests() throws IOException {

        Properties properties = getFacebookConnectionProperties();

        applicationID = properties.getProperty("facebook.appId1");
        aplicationSecret = properties.getProperty("facebook.appSecret1");

        facebookStore = new FacebookTestUserStore(applicationID, aplicationSecret);

        facebookStore.deleteAllTestUsers();

        helper = new Helper();

    }

    @Test
    public void test001_Make_Friends() {

        testUser1 = createAccount();
        testUser2 = createAccount();

        testUser1.makeFriends(testUser2);

        String friends1 = testUser1.getFriends();
        String friends2 = testUser2.getFriends();

        Assert.assertTrue(friends1.contains(testUser2.id()));
        Assert.assertTrue(friends2.contains(testUser1.id()));

    }

    @Test
    public void test002_delete_Test_User() {

        testUser1 = createAccount();

        testUser1.delete();

    }

    public FacebookTestUserAccount createAccount() {

        String jsonResponse = facebookStore.post("/%s/accounts/test-users",
                helper.buildList("installed", "true", "permissions", "read_stream,publish_actions,user_photos"), null, applicationID);

        JsonElement user = helper.parseJsonObject(jsonResponse);

        FacebookTestUserAccount account;

        account = new FacebookTestUserAccount(facebookStore, user);


        String userdetails = account.getUserDetails();

        accessToken = account.accessToken();

        userId = user.getAsJsonObject().get("id").getAsString();

        return account;

    }

}
