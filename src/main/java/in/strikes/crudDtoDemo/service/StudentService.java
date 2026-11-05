package in.strikes.crudDtoDemo.service;

import in.strikes.crudDtoDemo.dto.*;
import in.strikes.crudDtoDemo.entity.Student;
import in.strikes.crudDtoDemo.exception.DuplicateResourceException;
import in.strikes.crudDtoDemo.exception.ResourceNotFoundException;
import in.strikes.crudDtoDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public ResponseDto createStudent(RequestDto studentReq){

        Student entity = mapToEntity(studentReq);
        if(emailExists(entity)){
            throw new DuplicateResourceException("Student with email "+entity.getEmail()
                    +" already exists!!");
        }
        Student createStudent = studentRepository.save(entity);

        return mapToDto(createStudent);
    }

    public GetResponseDto getStudent(Long id){
        Student studentResp = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id "+ id +" does not exists!!"));

        return mapToGetDto(studentResp);
    }

    public List<GetResponseDto> getAllStudent(){
        List<Student> studentList = studentRepository.findByAndDeletedIsFalse();

        return studentList.stream().map(this::mapToGetDto).toList();
    }

    public UpdateResponseDto updateStudent(Long id, UpdateRequestDto studentReq){
        Student existingStudent = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("Student with id "+ id +" does not exists!!") );

        existingStudent.setName(studentReq.getName());
        existingStudent.setRno(studentReq.getRno());
        existingStudent.setSubject(studentReq.getSubject());
        existingStudent.setAge(studentReq.getAge());
        existingStudent.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(existingStudent);

        return mapToUpdateDto(existingStudent);
    }

    public void deleteStudentRecord(Long id){
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id "+ id +" does not exists!!"));

        studentRepository.delete(student);
    }

    public void deleteStudentSoftly(Long id){
        Student existingStudent = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id "+ id +" does not exists!!"));

        existingStudent.setDeleted(true);
        studentRepository.save(existingStudent);
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
        studentResp.setMessage("Student Created Successfully");

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

    private boolean emailExists(Student student){
        return studentRepository.findByEmail(student.getEmail());
    }
}
