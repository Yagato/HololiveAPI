package com.yagato.HololiveAPI.service.impl;

import com.yagato.HololiveAPI.model.Rigger;
import com.yagato.HololiveAPI.repository.RiggerRepository;
import com.yagato.HololiveAPI.service.RiggerService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return riggerRepository.findById(id);
    }

    @Override
    public Rigger findByName(String name) {
        return riggerRepository.findByName(name);
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
