package com.yagato.HololiveAPI.controller;

import com.yagato.HololiveAPI.exception.ApiRequestException;
import com.yagato.HololiveAPI.model.Illustrator;
import com.yagato.HololiveAPI.service.IllustratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/illustrators")
@RequiredArgsConstructor
public class IllustratorController {

    private final IllustratorService illustratorService;

    @GetMapping("/all")
    public List<Illustrator> findAll() {
        return illustratorService.findAllByOrderById();
    }

    @GetMapping("/{illustratorId}")
    public Illustrator findIllustratorById(@PathVariable int illustratorId) {
        Illustrator illustrator = illustratorService.findById(illustratorId);

        if(illustrator == null) {
            throw new ApiRequestException("Illustrator id not found - " + illustratorId);
        }

        return illustrator;
    }

    @PostMapping("/new")
    public Illustrator addIllustrator(@RequestBody Illustrator illustrator) {
        illustrator.setId(0);

        return illustratorService.save(illustrator);
    }

    @PutMapping("/update")
    public Illustrator updateIllustrator(@RequestBody Illustrator illustrator) {
        return illustratorService.save(illustrator);
    }

    @DeleteMapping("/{illustratorId}")
    public String deleteIllustrator(@PathVariable int illustratorId) {
        Illustrator tempIllustrator = illustratorService.findById(illustratorId);

        if(tempIllustrator == null) {
            throw new ApiRequestException("Illustrator id not found - " + illustratorId);
        }

        illustratorService.deleteById(illustratorId);

        return "Deleted illustrator id - " + illustratorId;
    }

}
