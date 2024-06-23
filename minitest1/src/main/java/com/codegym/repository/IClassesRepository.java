package com.codegym.repository;

import com.codegym.model.Classes;
import com.codegym.model.dto.IClassesCount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IClassesRepository extends CrudRepository<Classes, Long> {
    @Query(nativeQuery = true, value ="select cl.name as classes, count(st.id) as studentNumber \n" +
            "from classes cl\n" +
            "left join students st on cl.id = st.classes_id\n" +
            "group by cl.id\n" +
            "order by cl.name;")
    List<IClassesCount> getClassesCounts();
}
