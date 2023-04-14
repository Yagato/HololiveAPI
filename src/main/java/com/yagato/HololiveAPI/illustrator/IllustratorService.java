package com.yagato.HololiveAPI.illustrator;

import java.util.List;

public interface IllustratorService {

    List<Illustrator> findAll();

    List<Illustrator> findAllByOrderById();

    Illustrator findById(int id);

    Illustrator save(Illustrator illustrator);

    void deleteById(int id);

}
