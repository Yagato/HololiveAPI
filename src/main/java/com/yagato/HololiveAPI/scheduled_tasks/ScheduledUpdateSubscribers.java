package com.yagato.HololiveAPI.scheduled_tasks;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.yagato.HololiveAPI.talent.Talent;
import com.yagato.HololiveAPI.talent.TalentService;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledUpdateSubscribers {

    private final TalentService talentService;
    private final Logger logger = LoggerFactory.getLogger(ScheduledUpdateSubscribers.class);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
    Dotenv dotenv = Dotenv.load();
    String YOUTUBE_API_KEY = dotenv.get("YOUTUBE_API_KEY");

    @Autowired
    public ScheduledUpdateSubscribers(TalentService talentService) {
        this.talentService = talentService;
    }

    // cron's patterns: second, minute, hour, day, month, weekday, year (year is optional)
    // Examples:
    // 0 0 * * * * = the top of every hour of every day.
    // */10 * * * * * = every ten seconds.
    // 0 0 8-10 * * * = 8, 9 and 10 o'clock of every day.
    // 0 0 6,19 * * * = 6:00 AM and 7:00 PM every day.
    // 0 0/30 8-10 * * * = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
    // 0 0 9-17 * * MON-FRI = on the hour nine-to-five weekdays
    // 0 0 0 25 12 ? = every Christmas Day at midnight
    // More info at: https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
    @Scheduled(cron = "0 0 */8 * * *") // run this task every 8 hours
    public void updateSubscribers() throws UnirestException {
        List<Talent> talents = talentService.findAllByOrderById();

        for(Talent talent : talents) {
            String channelId = talent.getChannelId();


            if(channelId == null) {
                logger.info("Skipping talent: " + talent.getName());
                continue;
            }

            String uri = "https://www.googleapis.com/youtube/v3/channels?part=statistics&id="
                    + channelId + "&key=" + YOUTUBE_API_KEY;

            HttpResponse<JsonNode> response = Unirest.get(uri).asJson();
            JSONObject responseBody = response.getBody().getObject();

            if(!responseBody.has("items")) {
                logger.info("Skipping talent: " + talent.getName());
                continue;
            }

            JSONObject items = responseBody.getJSONArray("items").getJSONObject(0);
            JSONObject statistics = items.getJSONObject("statistics");
            String subscribers = statistics.getString("subscriberCount");

            talent.setSubscribers(Integer.parseInt(subscribers));

            talentService.save(talent);

            logger.info("Talent: " + talent.getName() + " | " +
                    " Subscribers: " + subscribers + " | " +
                    " updated at {}", simpleDateFormat.format((new Date())));
        }

        logger.info("Finished updating talents at {}", simpleDateFormat.format(new Date()));
    }
}
