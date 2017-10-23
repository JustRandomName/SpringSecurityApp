package net.proselyte.springsecurityapp.service;


import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


@Service
public class TwitterServiceImp implements TwitterService {
    private Twitter twitter;
    private RequestToken requestToken;
    private AccessToken accessToken;

    public TwitterServiceImp() throws TwitterException {
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("wubSydqOjSp1YXn0nFWTsGUex", "AYNqEMwcMBDsGRAcHQpcVes4XGYSMBrpmvs7cK5bVNtegfQnTK");
        requestToken = twitter.getOAuthRequestToken("http://localhost:8087/tw");
    }

    @Override
    public String getAuthURL() {
        return requestToken.getAuthenticationURL();
    }

    @Override
    public AccessToken getAccessToken(String oauth_verifier) {
        try {
            if (accessToken == null) accessToken=twitter.getOAuthAccessToken(requestToken, oauth_verifier);
            return accessToken;
        } catch (Exception e) {

        }
        return null;
    }
}

