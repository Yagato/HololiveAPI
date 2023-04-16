package com.yagato.HololiveAPI.imgur;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ImgurClient {

    private String CLIENT_ID_IMGUR;

    private static final Logger logger = LoggerFactory.getLogger(ImgurClient.class);

    @PostConstruct
    private void load() {
        Dotenv dotenv = Dotenv.load();
        CLIENT_ID_IMGUR = dotenv.get("CLIENT_ID_IMGUR");
    }

    public String uploadImage(String base64String) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("https://api.imgur.com/3/image")
                .header("Authorization", "Client-ID " + CLIENT_ID_IMGUR)
                .field("image", base64String)
                .asJson();

        JSONObject jsonObject = response.getBody().getObject();

        System.out.println(jsonObject);

        JSONObject data = jsonObject.getJSONObject("data");

        String link = data.getString("link");

        logger.info("Image uploaded successfully. Link: " + link);

        return link;
    }
}
