package com.example.firstspringboot.service;

import com.example.firstspringboot.entity.Tutorial;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.firstspringboot.repository.TutorialRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutorialService {
    private final TutorialRepo repo;
    public List<Tutorial> getAll(){
        return repo.findAll();
    }
    public Tutorial getById(Long id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    }
    public Tutorial create(Tutorial tutorial){
        return repo.save(tutorial);
    }
    public Tutorial update(Long id, Tutorial data){
        Tutorial tutorial = getById(id);
        tutorial.setTitle(data.getTitle());
        tutorial.setDescription(data.getDescription());
        tutorial.setPublished(data.isPublished());

        return repo.save(tutorial);
    }
    public void delete(long id){
        repo.deleteById(id);
    }
}
