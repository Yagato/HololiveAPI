package com.yagato.HololiveAPI.talent;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.yagato.HololiveAPI.imgur.ImgurClient;
import com.yagato.HololiveAPI.talent.support_entities.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TalentController {

    private TalentService talentService;
    private ImgurClient imgurClient;

    @Autowired
    public TalentController(TalentService talentService, ImgurClient imgurClient) {
        this.talentService = talentService;
        this.imgurClient = imgurClient;
    }

    @GetMapping("/talents")
    public List<Talent> findAll() {
        return talentService.findAllByOrderById();
    }

    @GetMapping("/talents/name={name}")
    public Talent findByName(@PathVariable String name) {
        name = name.replace("_", " ");

        Talent talent = talentService.findByName(name);

        if(talent == null) {
            throw new RuntimeException("Didn't find talent - " + name);
        }

        return talent;
    }

    @GetMapping("/talents/channelId={channelId}")
    public Talent findByChannelId(@PathVariable String channelId) {
        Talent talent = talentService.findByChannelId(channelId);

        if(talent == null) {
            throw new RuntimeException("Didn't find talent with channel ID - " + channelId);
        }

        return talent;
    }

    @PostMapping(
            value = "/talents",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public Talent addTalent(@RequestPart(name = "image", required = false) MultipartFile[] image,
                            @RequestPart(name = "talent") Talent talent) throws IOException, UnirestException {
        talent.setId(0);

        if(talent.getAltNames() != null)
            talent.getAltNames().setTalent(talent);

        if(talent.getHashtags() != null)
            talent.getHashtags().setTalent(talent);

        List<Model> models = talent.getModels();

        if(models != null) {
            for(int i = 0; i < models.size(); i++) {
                models.get(i).setId(0);
                String base64URL = Base64.getEncoder().encodeToString(image[i].getBytes());
                String link = imgurClient.uploadImage(base64URL);
                models.get(i).setTalent(talent);
                models.get(i).setImageURL(link);
            }
        }

        if(talent.getSocialMedia() != null)
            talent.getSocialMedia().setTalent(talent);

        return talentService.save(talent);
    }

    @PutMapping(
            value = "/talents",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public Talent updateTalent(@RequestPart(name = "image", required = false) MultipartFile[] image,
                               @RequestPart(name = "talent") Talent talent) throws IOException, UnirestException {

        Talent tempTalent = talentService.findByName(talent.getName());

        /*
        if(talent.getAltNames() != null)
            talent.getAltNames().setTalent(talent);

        if(talent.getHashtags() != null)
            talent.getHashtags().setTalent(talent);

         */

        if(talent.getModels().size() > tempTalent.getModels().size()) {
            List<Model> models = talent.getModels();
            System.out.println("Old: " + tempTalent.getModels().size());
            System.out.println("New: " + talent.getModels().size());
            for(int i = 0; i < models.size(); i++) {
                models.get(i).setId(0);
                String base64URL = Base64.getEncoder().encodeToString(image[i].getBytes());
                String link = imgurClient.uploadImage(base64URL);
                models.get(i).setTalent(talent);
                models.get(i).setImageURL(link);
            }
        }

        /*
        if(talent.getSocialMedia() != null)
            talent.getSocialMedia().setTalent(talent);

         */

        return talentService.save(talent);
    }

    @DeleteMapping("/talents/{name}")
    public String deleteTalent(@PathVariable String name) {
        name = name.replace("_", " ");

        Talent talent = talentService.findByName(name);

        if(talent == null) {
            throw new RuntimeException("Couldn't find talent - " + name);
        }

        talentService.deleteByName(name);

        return "Deleted talent " + name;
    }
}
