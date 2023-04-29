package com.yagato.HololiveAPI.controller;

import com.yagato.HololiveAPI.model.Illustrator;
import com.yagato.HololiveAPI.service.IllustratorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IllustratorController {

    private final IllustratorService illustratorService;

    public IllustratorController(IllustratorService illustratorService) {
        this.illustratorService = illustratorService;
    }

    @GetMapping("/illustrators")
    public List<Illustrator> findAll() {
        return illustratorService.findAllByOrderById();
    }

    @GetMapping("/illustrators/{illustratorId}")
    public Illustrator findIllustratorById(@PathVariable int illustratorId) {
        Illustrator illustrator = illustratorService.findById(illustratorId);

        if(illustrator == null) {
            throw new RuntimeException("Illustrator id not found - " + illustratorId);
        }

        return illustrator;
    }

    @PostMapping("/illustrators")
    public Illustrator addIllustrator(@RequestBody Illustrator illustrator) {
        illustrator.setId(0);

        return illustratorService.save(illustrator);
    }

    @PutMapping("/illustrators")
    public Illustrator updateIllustrator(@RequestBody Illustrator illustrator) {
        return illustratorService.save(illustrator);
    }

    @DeleteMapping("/illustrators/{illustratorId}")
    public String deleteIllustrator(@PathVariable int illustratorId) {
        Illustrator tempIllustrator = illustratorService.findById(illustratorId);

        if(tempIllustrator == null) {
            throw new RuntimeException("Illustrator id not found - " + illustratorId);
        }

        illustratorService.deleteById(illustratorId);

        return "Deleted illustrator id - " + illustratorId;
    }

}
