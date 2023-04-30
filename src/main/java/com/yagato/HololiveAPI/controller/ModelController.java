package com.yagato.HololiveAPI.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.yagato.HololiveAPI.model.Illustrator;
import com.yagato.HololiveAPI.model.Model;
import com.yagato.HololiveAPI.model.Rigger;
import com.yagato.HololiveAPI.model.Talent;
import com.yagato.HololiveAPI.service.IllustratorService;
import com.yagato.HololiveAPI.service.ModelService;
import com.yagato.HololiveAPI.service.RiggerService;
import com.yagato.HololiveAPI.service.TalentService;
import com.yagato.HololiveAPI.utils.ImgurClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;
    private final TalentService talentService;
    private final ImgurClient imgurClient;
    private final IllustratorService illustratorService;
    private final RiggerService riggerService;

    @GetMapping("/{talentId}")
    public List<Model> findByTalentId(@PathVariable int talentId) {
        return modelService.findByTalentId(talentId);
    }

    @PostMapping(
            value = "/new",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public Model addModel(@RequestPart(name = "image") MultipartFile image,
                          @RequestPart("model") Model model,
                          @RequestPart("talent_name") String talentName) throws IOException, UnirestException {

        Talent talent = talentService.findByName(talentName);

        model.setId(0);

        List<Illustrator> illustrators = model.getIllustrators();

        if (illustrators != null) {
            for (Illustrator illustrator : illustrators) {
                Illustrator tempIllustrator = illustratorService.findByName(illustrator.getName());
                System.out.println(tempIllustrator);

                if (tempIllustrator == null) {
                    illustrator.setId(0);
                    illustratorService.save(illustrator);
                }

                tempIllustrator = illustratorService.findByName(illustrator.getName());
                illustrator.setId(tempIllustrator.getId());
            }
        }

        List<Rigger> riggers = model.getRiggers();

        if (riggers != null) {
            for (Rigger rigger : riggers) {
                Rigger tempRigger = riggerService.findByName(rigger.getName());

                if (tempRigger == null) {
                    rigger.setId(0);
                    riggerService.save(rigger);
                }

                tempRigger = riggerService.findByName(rigger.getName());
                rigger.setId(tempRigger.getId());
            }
        }

        if (image != null) {
            String base64URL = Base64.getEncoder().encodeToString(image.getBytes());
            String link = imgurClient.uploadImage(base64URL);
            model.setImageURL(link);
        }

        model.setTalent(talent);

        return modelService.save(model);
    }

    // todo: add PUT mapping

    @DeleteMapping("/{modelId}")
    public String deleteModel(@PathVariable int modelId) {
        Model model = modelService.findById(modelId);

        if(model == null) {
            throw new RuntimeException("Couldn't find model - " + modelId);
        }

        modelService.deleteById(modelId);

        return "Deleted model " + modelId;
    }


}
