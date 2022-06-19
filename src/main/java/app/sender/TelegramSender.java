package app.sender;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class TelegramSender extends Sender {
    private final String attributeName = "document";
    private String fileEndpoint;
    private String getUpdates;
    private String textEndpoint;
    private final int clientId;

    public TelegramSender(String botToken, String username) throws IOException, ParseException {
        getUpdates = "https://api.telegram.org/bot" + botToken + "/getUpdates";
        clientId = getClientId(username);
        generateEndpoints(botToken, clientId);
    }

    public TelegramSender(String botToken, int clientId) {
        this.clientId = clientId;
        generateEndpoints(botToken, clientId);
    }

    private void generateEndpoints(String botToken, int clientId) {
        fileEndpoint = "https://api.telegram.org/bot" + botToken + "/sendDocument?chat_id=" + clientId;
        textEndpoint = "https://api.telegram.org/bot" + botToken + "/sendMessage?chat_id=" + clientId;
    }

    private int getClientId(String clientUsername) throws IOException, ParseException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httppost = new HttpPost(getUpdates);
            HttpEntity entity = httpclient.execute(httppost).getEntity();
            return parseJsonGetUpdates(entity, clientUsername);
        }
    }

    @Override
    public boolean sendFile(File file) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httppost = new HttpPost(fileEndpoint);
            httppost.setEntity(MultipartEntityBuilder.create().addPart(attributeName, new FileBody(file)).build());
            httpclient.execute(httppost);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private int parseJsonGetUpdates(HttpEntity entity, String clientUsername) throws IOException, ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(EntityUtils.toString(entity));
        JSONArray array = (JSONArray) object.get("result");
        for (Object jsonObject : array) {
            JSONObject json = (JSONObject) jsonObject;
            json = (JSONObject) ((JSONObject) json.get("message")).get("chat");
            if (clientUsername.equals(json.get("username").toString()))
                return Integer.parseInt(json.get("id").toString());
        }
        throw new IOException();
    }

    @Override
    public boolean sendText(String text) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            textEndpoint = textEndpoint + "&text=" + text;
            HttpPost httpPost = new HttpPost(textEndpoint);
            httpclient.execute(httpPost);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public int getClientId() {
        return clientId;
    }
}
