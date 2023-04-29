package com.yagato.HololiveAPI.service.impl;

import com.yagato.HololiveAPI.model.Generation;
import com.yagato.HololiveAPI.repository.GenerationRepository;
import com.yagato.HololiveAPI.service.GenerationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenerationServiceImpl implements GenerationService {

    private GenerationRepository generationRepository;

    public GenerationServiceImpl(GenerationRepository generationRepository) {
        this.generationRepository = generationRepository;
    }


    @Override
    public List<Generation> findAll() {
        return generationRepository.findAll();
    }

    @Override
    public List<Generation> findAllByOrderById() {
        return generationRepository.findAllByOrderById();
    }

    @Override
    public Generation findByName(String name) {
        return generationRepository.findByName(name);
    }

    @Override
    public Generation findById(int id) {
        Optional<Generation> result = Optional.ofNullable(generationRepository.findById(id));

        Generation generation = null;

        if(result.isPresent()) {
            generation = result.get();
        }
        else {
            throw new RuntimeException("Couldn't find generation id - " + id);
        }

        return generation;
    }

    @Override
    public Generation save(Generation generation) {
        return generationRepository.save(generation);
    }

    @Override
    public void deleteById(int id) {
        generationRepository.deleteById(id);
    }
}
