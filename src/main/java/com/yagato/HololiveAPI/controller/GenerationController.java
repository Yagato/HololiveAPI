package com.yagato.HololiveAPI.controller;

import com.yagato.HololiveAPI.model.Generation;
import com.yagato.HololiveAPI.generation.GenerationResponse;
import com.yagato.HololiveAPI.service.GenerationService;
import com.yagato.HololiveAPI.model.Talent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                talentsNames.add(talent.getName());
            }

            generationResponse.setTalent(talentsNames);

            generationResponsesList.add(generationResponse);
        }

        return generationResponsesList;
    }

    @GetMapping("/generations/{generationId}")
    public GenerationResponse findGenerationById(@PathVariable int generationId) {
        Generation generation = generationService.findById(generationId);

        if(generation == null) {
            throw new RuntimeException("Generation id not found - " + generationId);
        }

        GenerationResponse generationResponse = new GenerationResponse();
        generationResponse.setId(generation.getId());
        generationResponse.setName(generation.getName());

        List<String> talentsNames = new ArrayList<>();
        List<Talent> talents = generation.getTalents();

        for(Talent talent : talents) {
            talentsNames.add(talent.getName());
        }

        generationResponse.setTalent(talentsNames);

        return generationResponse;
    }

    @PostMapping("/generations")
    public Generation addGeneration(@RequestBody Generation generation) {
        generation.setId(0);

        return generationService.save(generation);
    }

    @PutMapping("/generations")
    public Generation updateGeneration(@RequestBody Generation generation) {
        return generationService.save(generation);
    }

    @DeleteMapping("/generations/{generationId}")
    public String deleteGeneration(@PathVariable int generationId) {
        Generation tempGeneration = generationService.findById(generationId);

        if(tempGeneration == null) {
            throw new RuntimeException("Generation id not found - " + generationId);
        }

        generationService.deleteById(generationId);

        return "Deleted Generation with id - " + generationId;
    }

}
