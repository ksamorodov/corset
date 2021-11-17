package ru.saprcorset.backend.resourse;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KernelResourse {
    private final DSLContext dsl;
}
