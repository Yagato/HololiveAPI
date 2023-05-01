package com.yagato.HololiveAPI.service.impl;

import com.yagato.HololiveAPI.exception.ApiRequestException;
import com.yagato.HololiveAPI.model.Talent;
import com.yagato.HololiveAPI.repository.TalentRepository;
import com.yagato.HololiveAPI.service.TalentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TalentServiceImpl implements TalentService {

    private final TalentRepository talentRepository;

    @Override
    public List<Talent> findAll() {
        return talentRepository.findAll();
    }

    @Override
    public List<Talent> findAllByOrderById() {
        return talentRepository.findAllByOrderById();
    }

    @Override
    public Talent findByName(String name) {
        return talentRepository.findByName(name);
    }

    @Override
    public Talent findByChannelId(String channelId) {
        return talentRepository.findByChannelId(channelId);
    }

    @Override
    public Talent findById(int id) {
//        Optional<Talent> result = talentRepository.findById(id);
//
//        Talent talent = null;
//
//        if(result.isPresent()) {
//            talent = result.get();
//        }
//        else {
//            throw new RuntimeException("Didn't find talent id - " + id);
//        }

        return talentRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Couldn't find talent id - " + id));
    }

    @Override
    public Talent save(Talent talent) {
        return talentRepository.save(talent);
    }

    @Override
    public void deleteById(int id) {
        talentRepository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        talentRepository.deleteByName(name);
    }
}
