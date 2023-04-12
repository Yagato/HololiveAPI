package com.yagato.HololiveAPI.generation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yagato.HololiveAPI.talent.Talent;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/generations")
    public Map<Integer, Object> findAll() {
        Map<Integer, Object> result = new HashMap<>();

        List<Generation> generations = generationRepository.findAllByOrderById();

        int counter = 0;
        for(Generation g : generations) {
            Map<String, Object> map = new HashMap<>();

            List<String> talentsInThisGen = new ArrayList<>();

            map.put("id", g.getId());
            map.put("name", g.getName());

            List<Talent> talents = g.getTalents();
            for(Talent talent : talents) {
                talentsInThisGen.add(talent.getName());
            }
            map.put("talents", talentsInThisGen);

            result.put(counter, map);
            counter++;
        }

        return result;
    }


}
