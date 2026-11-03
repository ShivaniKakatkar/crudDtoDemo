package in.strikes.crudDtoDemo.service;

import in.strikes.crudDtoDemo.dto.*;
import in.strikes.crudDtoDemo.entity.Student;
import in.strikes.crudDtoDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public ResponseDto createStudent(RequestDto studentReq){
        System.out.println("Inside student service");
        Student entity = mapToEntity(studentReq);
        Student createStudent = studentRepository.save(entity);
        System.out.println("Exiting student service");
        ResponseDto studentResp = mapToDto(createStudent);
        studentResp.setMessage("Student Created Successfully");
        return studentResp;
    }

    public GetResponseDto getStudent(Long id){
        Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);
        if(studentResp.isEmpty()){
            return null;
        }
        GetResponseDto getStudent = mapToGetDto(studentResp);

        return getStudent;

    }

    public List<GetResponseDto> getAllStudent(){
        List<Student> studentResp = studentRepository.findByAndDeletedIsFalse();

        if (studentResp.isEmpty()){
            return null;
        }
        List<GetResponseDto> getStudent = new ArrayList<>();
        for(Student student : studentResp){
            GetResponseDto eachStudent = mapToGetDto(student);
            getStudent.add(eachStudent);
        }

        return getStudent;
    }

    public UpdateResponseDto updateStudent(Long id, UpdateRequestDto studentReq){
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if (existingStudent.isEmpty()){
            return null;
        }

        Student studentToSave = existingStudent.get();

        studentToSave.setName(studentReq.getName());
        studentToSave.setRno(studentReq.getRno());
        studentToSave.setSubject(studentReq.getSubject());
        studentToSave.setAge(studentReq.getAge());
        studentToSave.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(studentToSave);
        UpdateResponseDto updateStudent = mapToUpdateDto(studentToSave);

        return updateStudent;

    }

    public Boolean deleteStudentRecord(Long id){
        Boolean isStudent = studentRepository.existsById(id);
        if (!isStudent) return false;

        studentRepository.deleteById(id);
        return true;
    }

    public Boolean deleteStudentSoftly(Long id){
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);

        if (existingStudent.isEmpty()){
            return false;
        }
        Student studentToSave = existingStudent.get();
        studentToSave.setDeleted(true);
        studentRepository.save(studentToSave);
        return true;
    }

    private Student mapToEntity(RequestDto studentReq){
        Student newStudent = new Student();
        newStudent.setName(studentReq.getName());
        newStudent.setEmail(studentReq.getEmail());
        newStudent.setAge(studentReq.getAge());
        newStudent.setRno(studentReq.getRno());
        newStudent.setSubject(studentReq.getSubject());
        newStudent.setCreatedAt(LocalDateTime.now());
        newStudent.setUpdatedAt(LocalDateTime.now());
        return newStudent;
    }
    private ResponseDto mapToDto(Student student){
        ResponseDto studentResp = new ResponseDto();
        studentResp.setId(student.getId());
        studentResp.setName(student.getName());
        studentResp.setAge(student.getAge());
        studentResp.setEmail(student.getEmail());
        studentResp.setRno(student.getRno());
        studentResp.setSubject(student.getSubject());
        studentResp.setCreatedAt(LocalDateTime.now());
        studentResp.setUpdatedAt(LocalDateTime.now());

        return studentResp;
    }

    private UpdateResponseDto mapToUpdateDto(Student updateStudent){
        UpdateResponseDto updatedStudent = new UpdateResponseDto();

        updatedStudent.setId(updateStudent.getId());
        updatedStudent.setName(updateStudent.getName());
        updatedStudent.setAge(updateStudent.getAge());
        updatedStudent.setRno(updateStudent.getRno());
        updatedStudent.setSubject(updateStudent.getSubject());
        updatedStudent.setUpdatedAt(LocalDateTime.now());
        updatedStudent.setMessage("Student Updated Successfully");
        return updatedStudent;
    }
    private GetResponseDto mapToGetDto(Student student){
        GetResponseDto studentResp = new GetResponseDto();
        studentResp.setId(student.getId());
        studentResp.setName(student.getName());
        studentResp.setAge(student.getAge());
        studentResp.setEmail(student.getEmail());
        studentResp.setRno(student.getRno());
        studentResp.setSubject(student.getSubject());
        studentResp.setCreatedAt(LocalDateTime.now());
        studentResp.setUpdatedAt(LocalDateTime.now());

        return studentResp;
    }
    private GetResponseDto mapToGetDto(Optional<Student> studentResp){
        GetResponseDto getStudent = new GetResponseDto();

        getStudent.setId(studentResp.get().getId());
        getStudent.setName(studentResp.get().getName());
        getStudent.setAge(studentResp.get().getAge());
        getStudent.setEmail(studentResp.get().getEmail());
        getStudent.setRno(studentResp.get().getRno());
        getStudent.setSubject(studentResp.get().getSubject());
        getStudent.setCreatedAt(studentResp.get().getCreatedAt());
        getStudent.setUpdatedAt(studentResp.get().getUpdatedAt());

        return getStudent;
    }
}
