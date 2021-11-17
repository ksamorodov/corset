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

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {

    }

    @Transactional
    public Optional<Integer> save(ConstructionsDTO constructionsDTO) {
        return constructionsResource.save(constructionsDTO);
    }

    @Transactional
    public Optional<ConstructionsDTO> getById(Integer id) {
        return constructionsResource.getById(id);
    }

    @Transactional
    public List<ConstructionsDTO> getKernelsList() {
        return constructionsResource.getConstructionsList();
    }
}
