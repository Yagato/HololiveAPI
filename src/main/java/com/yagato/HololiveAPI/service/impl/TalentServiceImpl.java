package com.yagato.HololiveAPI.service.impl;

import com.yagato.HololiveAPI.dto.TalentDto;
import com.yagato.HololiveAPI.model.Talent;
import com.yagato.HololiveAPI.repository.TalentRepository;
import com.yagato.HololiveAPI.service.TalentService;
import com.yagato.HololiveAPI.service.dto.TalentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TalentServiceImpl implements TalentService {

    private final TalentRepository talentRepository;
    private final TalentMapper talentMapper;

    @Override
    public List<TalentDto> findAll() {
        return talentRepository.findAll()
                .stream()
                .map(talentMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<TalentDto> findAllByOrderById() {
        return talentRepository.findAllByOrderById()
                .stream()
                .map(talentMapper)
                .collect(Collectors.toList());
    }

    @Override
    public TalentDto findByName(String name) {
        return talentRepository.findByName(name)
                .map(talentMapper)
                .orElseThrow(() -> new RuntimeException("Oops"));
    }

    @Override
    public TalentDto findByChannelId(String channelId) {
        return talentRepository.findByChannelId(channelId)
                .map(talentMapper)
                .orElseThrow(() -> new RuntimeException("Oops"));
    }

    @Override
    public TalentDto findById(int id) {
        Optional<TalentDto> result = talentRepository.findById(id).map(talentMapper);

        TalentDto talentDto = null;

        if (result.isPresent()) {
            talentDto = result.get();
        } else {
            throw new RuntimeException("Didn't find talent id - " + id);
        }

        return talentDto;
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
