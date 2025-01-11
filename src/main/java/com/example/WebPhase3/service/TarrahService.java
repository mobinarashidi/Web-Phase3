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

    public Optional<Tarrah> findById(String username) {
        return tarrahRepository.findById(username);
    }

    public Optional<Tarrah> findByUsername(String username) {
        return tarrahRepository.findByUsername(username);
    }

    public Tarrah save(Tarrah tarrah) {
        return tarrahRepository.save(tarrah);
    }

    public void deleteById(String username) {
        tarrahRepository.deleteById(username);
    }

    public boolean existsByUsername(String username) {
        return tarrahRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return tarrahRepository.existsByEmail(email);
    }
}
