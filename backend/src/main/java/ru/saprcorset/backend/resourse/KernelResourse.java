package ru.saprcorset.backend.resourse;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.saprcorset.backend.entity.KernelEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class KernelResourse {
    private final DSLContext dsl;

    @Transactional
    public int save(KernelEntity kernelEntity) {
        //dsl.batchInsert();
        return 0;
    }

    @Transactional
    public KernelEntity getById(Long id) {
        //dsl.batchInsert();
        return null;
    }

    @Transactional
    public List<KernelEntity> getListAccounts() {
        //dsl.batchInsert();
        return null;
    }
}
