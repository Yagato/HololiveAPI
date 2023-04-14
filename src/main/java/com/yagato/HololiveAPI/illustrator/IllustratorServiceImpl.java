package com.yagato.HololiveAPI.illustrator;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Illustrator> result = Optional.ofNullable(illustratorRepository.findById(id));

        Illustrator illustrator = null;

        if(result.isPresent()) {
            illustrator = result.get();
        }
        else {
            throw new RuntimeException("Couldn't find illustrator id - " + id);
        }

        return illustrator;
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
