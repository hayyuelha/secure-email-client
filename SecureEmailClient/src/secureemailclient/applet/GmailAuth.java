/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package secureemailclient.applet;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleOAuthConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

/**
 *
 * @author Ahmad Zaky
 */
public class GmailAuth {
    // Check https://developers.google.com/gmail/api/auth/scopes for all available scopes
    private static final String SCOPE = "https://mail.google.com";
    private static final String APP_NAME = "Gmail API Quickstart";
    // Email address of the user, or "me" can be used to represent the currently authorized user.
    private static final String USER = "me";
    private static String CLIENT_SECRET_JSON = "{\"installed\":{\"auth_uri\":\"https://accounts.google.com/o/oauth2/auth\",\"client_secret\":\"YMmRlSKThWVb1HR1gfZ_1uCC\",\"token_uri\":\"https://accounts.google.com/o/oauth2/token\",\"client_email\":\"\",\"redirect_uris\":[\"urn:ietf:wg:oauth:2.0:oob\",\"oob\"],\"client_x509_cert_url\":\"\",\"client_id\":\"1040270241513-rasnlf68im6ul5qf4d8a4jlvq7o8phao.apps.googleusercontent.com\",\"auth_provider_x509_cert_url\":\"https://www.googleapis.com/oauth2/v1/certs\"}}";

    private static GoogleClientSecrets clientSecrets = null;
    private static GoogleAuthorizationCodeFlow flow = null;
    private static GoogleCredential credential = null;
    private static Gmail gmail = null;
    private static String authUrl = null;

    public static String obtainAuthUrl() throws IOException {
        if (authUrl != null) {
            return authUrl;
        }

        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        clientSecrets = GoogleClientSecrets.load(jsonFactory,  new StringReader(CLIENT_SECRET_JSON));

        // Allow user to authorize via url.
        flow = new GoogleAuthorizationCodeFlow.Builder(
            httpTransport, jsonFactory, clientSecrets, Arrays.asList(SCOPE))
            .setAccessType("online")
            .setApprovalPrompt("auto").build();

        authUrl = flow.newAuthorizationUrl().setRedirectUri(GoogleOAuthConstants.OOB_REDIRECT_URI)
            .build();

        return authUrl;
    }
    
    public static boolean checkCode(String code) {
        // no url was generated
        if (flow == null) {
            return false;
        }
        
        // get response from the code
        GoogleTokenResponse response;
        try {
            response = flow.newTokenRequest(code)
                .setRedirectUri(GoogleOAuthConstants.OOB_REDIRECT_URI).execute();
        } catch (TokenResponseException exception) {
            exception.printStackTrace();
            return false;
        } catch (IOException exception) { 
            exception.printStackTrace();
            return false;
        }
        
        // get new credential and service
        credential = new GoogleCredential().setFromTokenResponse(response);
        
        // Create a new authorized Gmail API client
        gmail = new Gmail.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
            .setApplicationName(APP_NAME).build();
        
        return true;
    }
    
    public static Gmail getService() {
        return gmail;
    }
}
