package com.yagato.HololiveAPI.controller;

import com.yagato.HololiveAPI.model.Generation;
import com.yagato.HololiveAPI.response.GenerationResponse;
import com.yagato.HololiveAPI.service.GenerationService;
import com.yagato.HololiveAPI.model.Talent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/generations")
@RequiredArgsConstructor
public class GenerationController {

    private final GenerationService generationService;

    @GetMapping(value = "/all")
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

    @GetMapping("/{generationId}")
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

    @PostMapping("/new")
    public Generation addGeneration(@RequestBody Generation generation) {
        generation.setId(0);

        return generationService.save(generation);
    }

    @PutMapping("/update")
    public Generation updateGeneration(@RequestBody Generation generation) {
        return generationService.save(generation);
    }

    @DeleteMapping("/{generationId}")
    public String deleteGeneration(@PathVariable int generationId) {
        Generation tempGeneration = generationService.findById(generationId);

        if(tempGeneration == null) {
            throw new RuntimeException("Generation id not found - " + generationId);
        }

        generationService.deleteById(generationId);

        return "Deleted Generation with id - " + generationId;
    }

}
