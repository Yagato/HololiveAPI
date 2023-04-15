package com.yagato.HololiveAPI.talent;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.yagato.HololiveAPI.illustrator.Illustrator;
import com.yagato.HololiveAPI.illustrator.IllustratorService;
import com.yagato.HololiveAPI.imgur.ImgurClient;
import com.yagato.HololiveAPI.rigger.Rigger;
import com.yagato.HololiveAPI.rigger.RiggerService;
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

    private final TalentService talentService;
    private final IllustratorService illustratorService;
    private final RiggerService riggerService;
    private final ImgurClient imgurClient;

    @Autowired
    public TalentController(TalentService talentService,
                            ImgurClient imgurClient,
                            IllustratorService illustratorService,
                            RiggerService riggerService) {
        this.talentService = talentService;
        this.imgurClient = imgurClient;
        this.illustratorService = illustratorService;
        this.riggerService = riggerService;
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

                List<Illustrator> illustrators = models.get(i).getIllustrators();

                for(Illustrator illustrator : illustrators) {
                    if(illustratorService.findByName(illustrator.getName()) == null) {
                        System.out.println("INSIDE IF STATEMENT");
                        illustrator.setId(0);
                    }
                }

                List<Rigger> riggers = models.get(i).getRiggers();

                for(Rigger rigger : riggers) {
                    if(riggerService.findByName(rigger.getName()) == null) {
                        rigger.setId(0);
                    }
                }

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

    @PutMapping(value = "/talents")
    public Talent updateTalent(@RequestBody Talent talent) {
        Talent tempTalent = talentService.findByName(talent.getName());

        if(talent.getAltNames() != null) {
            talent.getAltNames().setId(tempTalent.getAltNames().getId());
            talent.getAltNames().setTalent(talent);
        }

        if(talent.getHashtags() != null) {
            talent.getHashtags().setId(tempTalent.getHashtags().getId());
            talent.getHashtags().setTalent(talent);
        }

        List<Model> models = talent.getModels();

        if(models != null) {
            for(int i = 0; i < models.size(); i++) {
                models.get(i).setTalent(talent);
            }
        }

        if(talent.getSocialMedia() != null) {
            talent.getSocialMedia().setId(tempTalent.getSocialMedia().getId());
            talent.getSocialMedia().setTalent(talent);
        }

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
