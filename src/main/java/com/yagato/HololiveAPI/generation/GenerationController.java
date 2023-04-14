package com.yagato.HololiveAPI.generation;

import com.yagato.HololiveAPI.talent.Talent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GenerationController {

    private final GenerationService generationService;

    @Autowired
    public GenerationController(GenerationService generationService) {
        this.generationService = generationService;
    }

    @GetMapping(value = "/generations")
    public List<GenerationResponse> findAll() {
        List<GenerationResponse> generationResponsesList = new ArrayList<>();

        List<Generation> generations = generationService.findAllByOrderById();

        for(Generation g : generations) {
            GenerationResponse generationResponse = new GenerationResponse();

            List<String> talentsNames = new ArrayList<>();

            generationResponse.setId(g.getId());
            generationResponse.setName(g.getName());

            List<Talent> talents = g.getTalents();

            for(Talent talent : talents) {
                if(talent.getActive())
                    talentsNames.add(talent.getName());
            }

            generationResponse.setTalent(talentsNames);

            generationResponsesList.add(generationResponse);
        }

        return generationResponsesList;
    }

    @GetMapping("/generations/id={id}")
    public GenerationResponse findGenerationById(@PathVariable String id) {
        Generation generation = generationService.findById(id);

        if(generation == null) {
            throw new RuntimeException("Didn't find generation id - " + id);
        }

        GenerationResponse generationResponse = new GenerationResponse();
        generationResponse.setId(generation.getId());
        generationResponse.setName(generation.getName());

        List<String> talentsNames = new ArrayList<>();
        List<Talent> talents = generation.getTalents();

        for(Talent talent : talents) {
            if(talent.getActive())
                talentsNames.add(talent.getName());
        }

        generationResponse.setTalent(talentsNames);

        return generationResponse;
    }


}
