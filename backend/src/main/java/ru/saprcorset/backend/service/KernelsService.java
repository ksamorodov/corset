package ru.saprcorset.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.saprcorset.backend.dto.KernelDTO;
import ru.saprcorset.backend.resourse.KernelResourse;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

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
    public void save(KernelDTO kernelDTO) {
        kernelResourse.save(kernelDTO);
    }

    @Transactional
    public KernelDTO getById(Long id) {

        KernelDTO kernelDTO = kernelResourse.getById(id);

        return modelMapper.map(kernelDTO, KernelDTO.class);
    }

    @Transactional
    public List<KernelDTO> getKernelsList() {
        return kernelResourse.getListAccounts();
    }
}
