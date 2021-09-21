package ru.saprcorset.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.saprcorset.backend.dto.TestDTO;
import ru.saprcorset.backend.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/save")
    public TestDTO save(@RequestBody TestDTO testDTO) {
        return testService.save(testDTO);
    }

    @GetMapping("/list")
    public List<TestDTO> getListAccounts() {
        return testService.testDTOList();
    }

    @GetMapping("/get/{id}")
    public TestDTO getById(@PathVariable Integer id) {
        return testService.getById(id);
    }
}
