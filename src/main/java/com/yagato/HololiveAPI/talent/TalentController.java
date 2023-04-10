package com.yagato.HololiveAPI.talent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TalentController {

    private TalentService talentService;

    @Autowired
    public TalentController(TalentService talentService) {
        this.talentService = talentService;
    }

    @GetMapping("/talents")
    public List<Talent> findAll() {
        return talentService.findAllByOrderById();
    }

    @GetMapping("/talents/{name}")
    public Talent findByName(@PathVariable String name) {
        name = name.replace("_", " ");

        Talent talent = talentService.findByName(name);

        if(talent == null) {
            throw new RuntimeException("Didn't find talent - " + name);
        }

        return talent;
    }
}
