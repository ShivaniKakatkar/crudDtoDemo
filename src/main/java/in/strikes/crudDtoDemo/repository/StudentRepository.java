package in.strikes.crudDtoDemo.repository;

import in.strikes.crudDtoDemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIdAndDeletedIsFalse(Long id);

    List<Student> findByAndDeletedIsFalse();

    Boolean findByEmail(String email);

}
