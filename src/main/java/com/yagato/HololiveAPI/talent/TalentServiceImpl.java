package com.yagato.HololiveAPI.talent;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TalentServiceImpl implements TalentService {

    private TalentRepository talentRepository;

    @Autowired
    public TalentServiceImpl(TalentRepository talentRepository) {
        this.talentRepository = talentRepository;
    }

    @Override
    public List<Talent> findAll() {
        return talentRepository.findAll();
    }

    @Override
    public List<Talent> findAllByOrderById() {
        return talentRepository.findAllByOrderById();
    }

    @Override
    public Talent findById(int id) {
        Optional<Talent> result = talentRepository.findById(id);

        Talent talent = null;

        if(result.isPresent()) {
            talent = result.get();
        }
        else {
            throw new RuntimeException("Didn't find talent id - " + id);
        }

        return talent;
    }

    @Override
    public Talent save(Talent talent) {
        return talentRepository.save(talent);
    }

    @Override
    public void deleteById(int id) {
        talentRepository.deleteById(id);
    }
}
