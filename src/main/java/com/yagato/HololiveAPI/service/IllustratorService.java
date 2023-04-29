package com.yagato.HololiveAPI.service;

import com.yagato.HololiveAPI.model.Illustrator;

import java.util.List;

public interface IllustratorService {

    List<Illustrator> findAll();

    List<Illustrator> findAllByOrderById();

    Illustrator findById(int id);

    Illustrator findByName(String name);

    Illustrator save(Illustrator illustrator);

    void deleteById(int id);

}
