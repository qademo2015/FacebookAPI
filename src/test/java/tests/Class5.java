import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import helpers.FacebookTestUserAccount;
import helpers.FacebookTestUserStore;
import helpers.Helper;
import helpers.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Class5 extends TestBase{


    @Test
    public void test001_Post_image() {

        account1 = facebookStore.createTestUser(true, "");

        String imageUrl = "https://apdt.com/images/dogs/dog-00033.jpg";

        accessToken = account1.accessToken();

        String response = facebookStore.post("/" + userId + "/photos",
                helper.buildList("url", imageUrl), helper.buildList("access_token", accessToken), applicationId);

        String getResponse = facebookStore.get("/" + userId + "/photos",
                helper.buildList("access_token", accessToken), null, applicationId);


    }

    @Test
    public void test002_Create_album() {

        account1 = facebookStore.createTestUser(true, "");


        accessToken = account1.accessToken();

        String albumName = "NewTestAlbum";

        String response = facebookStore.post("/" + userId + "/albums",
                helper.buildList("name", albumName), helper.buildList("access_token", accessToken), applicationId);

        String getResponse = facebookStore.get("/" + userId + "/albums",
                helper.buildList("access_token", accessToken), null, applicationId);

    }


}