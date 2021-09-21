package ru.saprcorset.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saprcorset.backend.dto.TestDTO;
import ru.saprcorset.backend.entity.TestEntity;
import ru.saprcorset.backend.mapper.TestMapper;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {

    }

    @Transactional
    public TestDTO save(TestDTO testDTO) {
        TestEntity testEntity = modelMapper.map(testDTO, TestEntity.class);
        testMapper.save(testEntity);

        return modelMapper.map(testEntity, TestDTO.class);
    }

    @Transactional
    public TestDTO getById(Integer id) {

        TestEntity testEntity = testMapper.getById(id);

        return modelMapper.map(testEntity, TestDTO.class);
    }

    @Transactional
    public List<TestDTO> testDTOList() {
        return testMapper.getListAccounts().stream()
                .map(testEntity -> modelMapper.map(testEntity, TestDTO.class))
                .collect(Collectors.toList());
    }
}
