package com.yagato.HololiveAPI.generation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenerationRepository extends JpaRepository<Generation, Integer> {

    List<Generation> findAllByOrderById();

    Generation findById(String id);
    Generation findByName(String name);

}
