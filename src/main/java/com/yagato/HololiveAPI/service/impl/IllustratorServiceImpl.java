package com.yagato.HololiveAPI.service.impl;

import com.yagato.HololiveAPI.model.Illustrator;
import com.yagato.HololiveAPI.repository.IllustratorRepository;
import com.yagato.HololiveAPI.service.IllustratorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IllustratorServiceImpl implements IllustratorService {

    private final IllustratorRepository illustratorRepository;

    public IllustratorServiceImpl(IllustratorRepository illustratorRepository) {
        this.illustratorRepository = illustratorRepository;
    }

    @Override
    public List<Illustrator> findAll() {
        return illustratorRepository.findAll();
    }

    @Override
    public List<Illustrator> findAllByOrderById() {
        return illustratorRepository.findAllByOrderById();
    }

    @Override
    public Illustrator findById(int id) {
        return illustratorRepository.findById(id);
    }

    @Override
    public Illustrator findByName(String name) {
        return illustratorRepository.findByName(name);
    }

    @Override
    public Illustrator save(Illustrator illustrator) {
        return illustratorRepository.save(illustrator);
    }

    @Override
    public void deleteById(int id) {
        illustratorRepository.deleteById(id);
    }
}
