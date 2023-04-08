package com.yagato.HololiveAPI.generation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GenerationController {

    private GenerationRepository generationRepository;

    @Autowired
    public GenerationController(GenerationRepository generationRepository) {
        this.generationRepository = generationRepository;
    }

    @GetMapping("/generations")
    public List<Generation> findAll() {
        return generationRepository.findAllByOrderById();
    }


}
