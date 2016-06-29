package helpers;

import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Properties;

import static tests.Class6.getFacebookConnectionProperties;

/**
 * Created by abarabash on 6/28/16.
 */
public class TestBase {

    public FacebookTestUserStore facebookStore;
    private String applicationID;
    private String aplicationSecret;
    private Helper helper;
    private FacebookTestUserAccount testUser1;
    private FacebookTestUserAccount testUser2;
    private String accessToken;
    private String userId;


    @BeforeClass
    public void beforeAllTests() throws IOException {

        Properties properties = getFacebookConnectionProperties();

        applicationID = properties.getProperty("facebook.appId1");
        aplicationSecret = properties.getProperty("facebook.appSecret1");

        facebookStore = new FacebookTestUserStore(applicationID, aplicationSecret);

        facebookStore.deleteAllTestUsers();

        helper = new Helper();

    }

}
