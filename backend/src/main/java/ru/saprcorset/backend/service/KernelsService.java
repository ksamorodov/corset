package ru.saprcorset.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saprcorset.backend.dto.KernelDTO;
import ru.saprcorset.backend.entity.KernelEntity;
import ru.saprcorset.backend.resourse.KernelResourse;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KernelsService {

    @Autowired
    private KernelResourse kernelResourse;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() {

    }

    @Transactional
    public KernelDTO save(KernelDTO kernelDTO) {
        KernelEntity kernelEntity = modelMapper.map(kernelDTO, KernelEntity.class);
        kernelResourse.save(kernelEntity);

        return modelMapper.map(kernelEntity, KernelDTO.class);
    }

    @Transactional
    public KernelDTO getById(Long id) {

        KernelEntity kernelEntity = kernelResourse.getById(id);

        return modelMapper.map(kernelEntity, KernelDTO.class);
    }

    @Transactional
    public List<KernelDTO> getKernelsList() {
        return kernelResourse.getListAccounts().stream()
                .map(kernelEntity -> modelMapper.map(kernelEntity, KernelDTO.class))
                .collect(Collectors.toList());
    }
}
