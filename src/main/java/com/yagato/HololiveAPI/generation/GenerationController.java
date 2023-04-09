package com.yagato.HololiveAPI.generation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.yagato.HololiveAPI.talent.Talent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GenerationController {

    private GenerationRepository generationRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public GenerationController(GenerationRepository generationRepository, ObjectMapper objectMapper) {
        this.generationRepository = generationRepository;
        this.objectMapper = objectMapper;
    }

    /*
    @GetMapping("/generations")
    public Map<String, Object> findAll() {
        Map<String, Object> map = new HashMap<>();

        List<Generation> generations = generationRepository.findAllByOrderById();

        for(Generation g : generations) {
            map.put("id", g.getId());
            map.put("name", g.getName());
            map.put("talents", g.getTalents());
        }

        return map;
    }
     */

    @GetMapping("/generations")
    public List<Generation> findAll() {
        return generationRepository.findAllByOrderById();
    }


}
