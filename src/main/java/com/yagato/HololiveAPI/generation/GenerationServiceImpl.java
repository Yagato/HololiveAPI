package com.yagato.HololiveAPI.generation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenerationServiceImpl implements GenerationService{

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
    public Generation findById(int id) {
        Optional<Generation> result = generationRepository.findById(id);

        Generation generation = null;

        if(result.isPresent()) {
            generation = result.get();
        }
        else {
            throw new RuntimeException("Didn't find generation id - " + id);
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
