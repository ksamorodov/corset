package ru.saprcorset.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;
import ru.saprcorset.backend.entity.TestEntity;

import java.util.List;

@Mapper
@Repository
public interface TestMapper {

    @SelectKey(resultType = Long.class, keyProperty = "id", before = true, statement = "select nextval('test_seq')")
    @Insert("insert into test (id, string, digit)" +
            "values (#{id}, #{string}, #{digit})")
    void save(TestEntity testEntity);

    @Select("select * from test")
    List<TestEntity> getListAccounts();


    @Select("select * from test where id = #{id}")
    TestEntity getById(Integer id);
}
