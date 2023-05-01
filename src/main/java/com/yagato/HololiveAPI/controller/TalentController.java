package com.yagato.HololiveAPI.controller;

import com.yagato.HololiveAPI.exception.ApiRequestException;
import com.yagato.HololiveAPI.model.Talent;
import com.yagato.HololiveAPI.service.TalentService;
import com.yagato.HololiveAPI.model.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/talents")
@RequiredArgsConstructor
public class TalentController {

    private final TalentService talentService;

    @GetMapping("/all")
    public List<Talent> findAll() {
        return talentService.findAllByOrderById();
    }

    @GetMapping("/name={name}")
    public Talent findByName(@PathVariable String name) {
        name = name.replace("_", " ");

        Talent talent = talentService.findByName(name);

        if (talent == null) {
            throw new ApiRequestException("Couldn't find talent - " + name);
        }

        return talent;
    }

    @GetMapping("/channelId={channelId}")
    public Talent findByChannelId(@PathVariable String channelId) {
        Talent talent = talentService.findByChannelId(channelId);

        if (talent == null) {
            throw new ApiRequestException("Couldn't find talent with channel ID - " + channelId);
        }

        return talent;
    }

    @PostMapping("/new")
    public Talent addTalent(@RequestBody Talent talent) {
        talent.setId(0);

        if (talent.getAltNames() != null)
            talent.getAltNames().setTalent(talent);

        if (talent.getHashtags() != null)
            talent.getHashtags().setTalent(talent);

        if (talent.getSocialMedia() != null)
            talent.getSocialMedia().setTalent(talent);

        return talentService.save(talent);
    }

    @PutMapping("/update")
    public Talent updateTalent(@RequestBody Talent talent) {
        Talent tempTalent = talentService.findById(talent.getId());

        if (talent.getAltNames() != null) {
            talent.getAltNames().setId(tempTalent.getAltNames().getId());
            talent.getAltNames().setTalent(talent);
        }

        if (talent.getHashtags() != null) {
            talent.getHashtags().setId(tempTalent.getHashtags().getId());
            talent.getHashtags().setTalent(talent);
        }

        List<Model> models = talent.getModels();

        if (models != null) {
            for (Model model : models) {
                if(model.getId() == null) {
                    model.setId(0);
                }
                model.setTalent(talent);
            }
        }

        if (talent.getSocialMedia() != null) {
            talent.getSocialMedia().setId(tempTalent.getSocialMedia().getId());
            talent.getSocialMedia().setTalent(talent);
        }

        return talentService.save(talent);
    }

    @DeleteMapping("/{name}")
    public String deleteTalent(@PathVariable String name) {
        name = name.replace("_", " ");

        Talent talent = talentService.findByName(name);

        if (talent == null) {
            throw new ApiRequestException("Couldn't find talent - " + name);
        }

        talentService.deleteByName(name);

        return "Deleted talent " + name;
    }
}
