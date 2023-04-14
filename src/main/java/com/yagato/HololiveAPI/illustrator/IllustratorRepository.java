package com.yagato.HololiveAPI.illustrator;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IllustratorRepository extends JpaRepository<Illustrator, Integer> {

    List<Illustrator> findAllByOrderById();

    Illustrator findById(int id);

}
