package com.yagato.HololiveAPI.talents;

import java.util.List;

public interface TalentService {

    List<Talent> findAll();

    List<Talent> findAllByOrderById();

    Talent findById(int id);

    Talent save(Talent talent);

    void deleteById(int id);

}
