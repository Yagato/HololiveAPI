package com.yagato.HololiveAPI.service;

import com.yagato.HololiveAPI.model.Generation;

import java.util.List;

public interface GenerationService {

    List<Generation> findAll();

    List<Generation> findAllByOrderById();

    Generation findById(int id);

    Generation findByName(String name);

    Generation save(Generation generation);

    void deleteById(int id);

}
