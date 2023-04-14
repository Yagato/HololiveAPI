package com.yagato.HololiveAPI.rigger;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RiggerServiceImpl implements RiggerService {

    private final RiggerRepository riggerRepository;

    public RiggerServiceImpl(RiggerRepository riggerRepository) {
        this.riggerRepository = riggerRepository;
    }

    @Override
    public List<Rigger> findAll() {
        return riggerRepository.findAll();
    }

    @Override
    public List<Rigger> findAllByOrderById() {
        return riggerRepository.findAllByOrderById();
    }

    @Override
    public Rigger findById(int id) {
        Optional<Rigger> result = Optional.ofNullable(riggerRepository.findById(id));

        Rigger rigger = null;

        if(result.isPresent()) {
            rigger = result.get();
        }
        else {
            throw new RuntimeException("Couldn't find rigger id - " + id);
        }

        return rigger;
    }

    @Override
    public Rigger save(Rigger rigger) {
        return riggerRepository.save(rigger);
    }

    @Override
    public void deleteById(int id) {
        riggerRepository.deleteById(id);
    }
}
