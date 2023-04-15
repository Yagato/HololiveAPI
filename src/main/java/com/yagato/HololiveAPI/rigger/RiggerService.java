package com.yagato.HololiveAPI.rigger;

import java.util.List;

public interface RiggerService {

    List<Rigger> findAll();

    List<Rigger> findAllByOrderById();

    Rigger findById(int id);

    Rigger findByName(String name);
    
    Rigger save(Rigger rigger);

    void deleteById(int id);

}
