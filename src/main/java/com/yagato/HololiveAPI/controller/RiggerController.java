package com.yagato.HololiveAPI.controller;

import com.yagato.HololiveAPI.model.Rigger;
import com.yagato.HololiveAPI.service.RiggerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RiggerController {

    private final RiggerService riggerService;

    public RiggerController(RiggerService riggerService) {
        this.riggerService = riggerService;
    }

    @GetMapping("/riggers")
    public List<Rigger> findAll() {
        return riggerService.findAllByOrderById();
    }

    @GetMapping("/riggers/{riggerId}")
    public Rigger findRiggerById(@PathVariable int riggerId) {
        Rigger rigger = riggerService.findById(riggerId);

        if(rigger == null) {
            throw new RuntimeException("Rigger id not found - " + riggerId);
        }

        return rigger;
    }

    @PostMapping("/riggers")
    public Rigger addRigger(@RequestBody Rigger rigger) {
        rigger.setId(0);

        return riggerService.save(rigger);
    }

    @PutMapping("/riggers")
    public Rigger updateRigger(@RequestBody Rigger rigger) {
        return riggerService.save(rigger);
    }

    @DeleteMapping("/riggers/{riggerId}")
    public String deleteRigger(@PathVariable int riggerId) {
        Rigger tempRigger = riggerService.findById(riggerId);

        if(tempRigger == null) {
            throw new RuntimeException("Rigger id not found - " + riggerId);
        }

        riggerService.deleteById(riggerId);

        return "Deleted rigger id - " + riggerId;
    }
}
