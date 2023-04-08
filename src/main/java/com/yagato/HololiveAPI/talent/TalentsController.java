package com.yagato.HololiveAPI.talent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TalentsController {

    private TalentRepository talentRepository;

    @Autowired
    public TalentsController(TalentRepository talentRepository) {
        this.talentRepository = talentRepository;
    }

    @GetMapping("/talents")
    public List<Talent> findAll() {
        return talentRepository.findAllByOrderById();
    }
}