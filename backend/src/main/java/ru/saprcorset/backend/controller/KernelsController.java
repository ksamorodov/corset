package ru.saprcorset.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.saprcorset.backend.dto.KernelDTO;
import ru.saprcorset.backend.service.KernelsService;

import java.util.List;

@RestController
@RequestMapping("/kernels")
public class KernelsController {

    @Autowired
    private KernelsService kernelsService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody KernelDTO kernelDTO) {
        kernelsService.save(kernelDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public List<KernelDTO> getKernelsList() {
        return kernelsService.getKernelsList();
    }

    @GetMapping("/get/{id}")
    public KernelDTO getById(@PathVariable Long id) {
        return kernelsService.getById(id);
    }
}
