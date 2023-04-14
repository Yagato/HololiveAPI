package com.yagato.HololiveAPI.generation;

import com.yagato.HololiveAPI.talent.Talent;

import java.util.List;

public interface GenerationService {

    List<Generation> findAll();

    List<Generation> findAllByOrderById();

    Generation findById(String id);

    Generation findByName(String name);

    Generation findById(int id);

    Generation save(Generation generation);

    void deleteById(int id);

}
