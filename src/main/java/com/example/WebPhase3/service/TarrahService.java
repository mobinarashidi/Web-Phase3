package com.example.WebPhase3.service;

import com.example.WebPhase3.model.Tarrah;
import com.example.WebPhase3.repository.TarrahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarrahService {

    @Autowired
    private TarrahRepository tarrahRepository;

    public List<Tarrah> findAll() {
        return tarrahRepository.findAll();
    }

    public Optional<Tarrah> findByUsername(String username) {
        return tarrahRepository.findByUsername(username);
    }

    public void save(Tarrah tarrah) {
        tarrahRepository.save(tarrah);
    }

    public boolean existsByUsername(String username) {
        return tarrahRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return tarrahRepository.existsByEmail(email);
    }
}
