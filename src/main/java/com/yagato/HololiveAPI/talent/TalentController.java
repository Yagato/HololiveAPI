package com.yagato.HololiveAPI.talent;

import com.yagato.HololiveAPI.generation.Generation;
import com.yagato.HololiveAPI.talent.support_entities.TalentGeneration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TalentController {

    private TalentRepository talentRepository;

    @Autowired
    public TalentController(TalentRepository talentRepository) {
        this.talentRepository = talentRepository;
    }

    /*
    @GetMapping("/talents")
    public List<Talent> findAll() {
        return talentRepository.findAllByOrderById();
    }
     */

    @GetMapping("/talents")
    public List<Talent> findAll() {

        /*
        Optional<Talent> tempTalent = talentRepository.findById(1);

        Talent talent = null;

        if(tempTalent.isPresent()) {
            talent = tempTalent.get();
            List<TalentGeneration> talentGeneration = talent.getTalentGeneration();
            for(TalentGeneration t : talentGeneration) {
                //System.out.println(t.getTalent());
                //System.out.println(t.getGeneration());
                Generation g = t.getGeneration();
                System.out.println(g.getId());
            }
        }
         */

        return talentRepository.findAllByOrderById();
    }
}
