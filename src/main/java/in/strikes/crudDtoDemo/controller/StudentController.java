package in.strikes.crudDtoDemo.controller;

import in.strikes.crudDtoDemo.dto.*;
import in.strikes.crudDtoDemo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }


    @PostMapping
    public ResponseEntity<ResponseDto> createStudent(
            @Valid @RequestBody RequestDto studentReq){

        ResponseDto createdStudent = studentService.createStudent(studentReq);

        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetResponseDto> getStudent(@PathVariable Long id){
        GetResponseDto studentResp = studentService.getStudent(id);

        return ResponseEntity.ok(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<GetResponseDto>> getAllStudent(){
        List<GetResponseDto> studentList = studentService.getAllStudent();

        return ResponseEntity.ok(studentList);
    }

    @PutMapping
    public ResponseEntity<UpdateResponseDto> updateStudent(@RequestParam Long id,
                                                 @RequestBody UpdateRequestDto studentReq){
        UpdateResponseDto studentResp = studentService.updateStudent(id,studentReq);

        return ResponseEntity.ok(studentResp);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam("id") Long id){
        studentService.deleteStudentRecord(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/delete-soft")
    public ResponseEntity<String> deleteStudentSoftly(@RequestParam("id") Long id){
        studentService.deleteStudentSoftly(id);

        return ResponseEntity.noContent().build();
    }
}
