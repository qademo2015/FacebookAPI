package helpers;

import com.google.gson.JsonElement;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by abarabash on 5/26/16.
 */
public class FacebookTestUserAccount {

    private static final Logger log = LoggerFactory.getLogger(FacebookTestUserAccount.class);

    private final FacebookTestUserStore helper;
    private JsonElement jsonUser;

    public FacebookTestUserAccount(FacebookTestUserStore helper, JsonElement user) {
        this.helper = helper;
        this.jsonUser = user;
    }

    public void delete() {
        String result = helper.delete("/%s", id());
        log.debug("Deleted account [{}]: [{}]", id(), result);
    }

    public void copyToOtherApplication(String applicationId, String accessToken, boolean appInstalled, String permissions) {
        String result = helper.post("/%s/accounts/test-users",
                helper.buildList("installed", Boolean.toString(appInstalled), "permissions", permissions == null ? "email, offline_access" : permissions, "owner_access_token", helper.getAccessToken()),
                helper.buildList("access_token", accessToken), applicationId);
        log.debug("Copied account: " + result);
    }

    public void copyToTestUserStore(FacebookTestUserStore testUserStore, boolean appInstalled, String permissions) {
        if (testUserStore instanceof FacebookTestUserStore) {
            FacebookTestUserStore knownStore = (FacebookTestUserStore) testUserStore;
            copyToOtherApplication(knownStore.getApplicationId(), knownStore.getAccessToken(), appInstalled, permissions);
        } else {
            throw new IllegalArgumentException("The provided testUserStore is of unknown type");
        }
    }

    public void makeFriends(FacebookTestUserAccount friend) {
        String requestResult = helper.post("/%s/friends/%s", null, helper.buildList("access_token", accessToken()), id(), friend.id());
        log.debug("Creating friend request: " + requestResult);
        String acceptResult = helper.post("/%s/friends/%s", null, helper.buildList("access_token", friend.accessToken()), friend.id(), id());
        log.debug("Accepting friend request: " + acceptResult);
    }

    public AccountSettingsChanger changeAccountSettings() {
        return new DefaultAccountSettingsChanger();
    }

    public String getFriends() {
        return get("/%s/friends", id());
    }

    public String getProfileFeed() {
        return get("/%s/feed", id());
    }

    public String getNewsFeed() {
        return get("/%s/home", id());
    }

    public String getLikes() {
        return get("/%s/likes", id());
    }

    public String getMovies() {
        return get("/%s/movies", id());

    }

    public String getMusic() {
        return get("/%s/music", id());
    }

    public String getBooks() {
        return get("/%s/books", id());
    }

    public String getNotes() {
        return get("/%s/notes", id());
    }

    public String getPhotoTags() {
        return get("/%s/photos", id());
    }

    public String getPhotoAlbums() {
        return get("/%s/albums", id());
    }

    public String getVideoTags() {
        return get("/%s/videos", id());
    }

    public String getVideoUploads() {
        return get("/%s/videos/uploaded", id());
    }

    public String getEvents() {
        return get("/%s/events", id());
    }

    public String getGroups() {
        return get("/%s/groups", id());
    }

    public String getCheckins() {
        return get("/%s/checkins", id());
    }

    public String getUserDetails() {
        return get("%s", id());
    }

    public String id() {
        return userDataAsString("id");
    }

    public String accessToken() {
        return userDataAsString("access_token");
    }

    public String loginUrl() {
        return userDataAsString("login_url");
    }

    public String getPassword() {
        return userDataAsString("password");
    }

    public String json() {
        return jsonUser.toString();
    }

    private String userDataAsString(String data) {
        if (jsonUser == null) {
            return null;
        }

        String anObject = jsonUser.getAsJsonObject().get(data).getAsString();

        return anObject != null ? anObject.toString() : null;
    }

    private String get(String resource, Object... pathParams) {
        return helper.get(resource, helper.buildList("access_token", accessToken()), pathParams);
    }

    private class DefaultAccountSettingsChanger implements AccountSettingsChanger {
        private List<NameValuePair> settings = new LinkedList<NameValuePair>();

        public AccountSettingsChanger newName(String name) {
            helper.appendToList(settings, "name", name);
            return this;
        }


        public AccountSettingsChanger newPassword(String password) {
            helper.appendToList(settings, "password", password);
            return this;
        }


        public void apply() {
            if (settings.size() > 0) {
                final String result = helper.post("/%s", settings, null, id());
                log.debug("Changed settings: " + result);
            }
        }


    }
}
