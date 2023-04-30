package com.yagato.HololiveAPI.controller;

import com.yagato.HololiveAPI.dto.TalentDto;
import com.yagato.HololiveAPI.model.Model;
import com.yagato.HololiveAPI.model.Talent;
import com.yagato.HololiveAPI.service.TalentService;
import com.yagato.HololiveAPI.service.dto.TalentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/talents")
public class TalentController {

    private final TalentService talentService;

    private final TalentMapper talentMapper;

    @GetMapping("/all")
    public List<TalentDto> findAll() {
        return talentService.findAllByOrderById();
    }

    @GetMapping("/name={name}")
    public TalentDto findByName(@PathVariable String name) {
        name = name.replace("_", " ");

        return talentService.findByName(name);
    }

    @GetMapping("/channelId={channelId}")
    public TalentDto findByChannelId(@PathVariable String channelId) {
        return talentService.findByChannelId(channelId);
    }

    @PostMapping("/new")
    public TalentDto addTalent(@RequestBody Talent talent) {
        talent.setId(0);

        if (talent.getAltNames() != null)
            talent.getAltNames().setTalent(talent);

        if (talent.getHashtags() != null)
            talent.getHashtags().setTalent(talent);

        if (talent.getSocialMedia() != null)
            talent.getSocialMedia().setTalent(talent);

        talentService.save(talent);

        return talentService.findByName(talent.getName());
    }

    @PutMapping("/update")
    public TalentDto updateTalent(@RequestBody Talent talent) {
        TalentDto tempTalentDto = talentService.findByName(talent.getName());

        if (talent.getAltNames() != null) {
            talent.getAltNames().setId(tempTalentDto.alternative_names().getId());
            talent.getAltNames().setTalent(talent);
        }

        if (talent.getHashtags() != null) {
            talent.getHashtags().setId(tempTalentDto.hashtags().getId());
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
            talent.getSocialMedia().setId(tempTalentDto.social_media().getId());
            talent.getSocialMedia().setTalent(talent);
        }

        talentService.save(talent);

        return talentService.findByName(talent.getName());
    }

    @DeleteMapping("/{name}")
    public String deleteTalent(@PathVariable String name) {
        name = name.replace("_", " ");

        TalentDto talentDto = talentService.findByName(name);

        if (talentDto == null) {
            throw new RuntimeException("Couldn't find talent - " + name);
        }

        talentService.deleteByName(name);

        return "Deleted talent " + name;
    }
}
