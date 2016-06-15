package tests;

import helpers.FacebookTestUserStore;
import helpers.Helper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
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


    public void getToken() throws URISyntaxException {

        HttpClient client = new DefaultHttpClient();

        HttpRequestBase requestBase = helper.buildGetResource("/oauth/access_token",
                helper.buildList("grant_type", "client_credentials", "client_id",
                        applicationId, "client_secret", applicationSecret));

        HttpResponse


    }

}
