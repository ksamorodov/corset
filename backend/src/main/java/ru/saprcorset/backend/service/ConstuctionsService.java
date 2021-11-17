package ru.saprcorset.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saprcorset.backend.dto.ConstructionsDTO;
import ru.saprcorset.backend.resourse.ConstructionsResource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Service
public class ConstuctionsService {

    @Autowired
    private ConstructionsResource constructionsResource;

    @PostConstruct
    public void init() {

    }

    public Optional<Integer> save(ConstructionsDTO constructionsDTO) {
        return constructionsResource.save(constructionsDTO);
    }

    public Optional<ConstructionsDTO> getById(Integer id) {
        return constructionsResource.getById(id);
    }

    public List<ConstructionsDTO> getKernelsList() {
        return constructionsResource.getConstructionsList();
    }

    public void deleteByName(String name) {
        constructionsResource.deleteByName(name);
    }
}
