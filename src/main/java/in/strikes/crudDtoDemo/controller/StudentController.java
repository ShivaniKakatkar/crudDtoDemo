package in.strikes.crudDtoDemo.controller;

import in.strikes.crudDtoDemo.dto.*;
import in.strikes.crudDtoDemo.entity.Student;
import in.strikes.crudDtoDemo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createStudent(
            @Valid @RequestBody RequestDto studentReq){

        System.out.println("Inside student controller");
        ResponseDto createdStudent = studentService.createStudent(studentReq);
        System.out.println("Exiting student controller");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);

    }

    @GetMapping("/get")
    public ResponseEntity<GetResponseDto> getStudent(@RequestParam Long id){
        GetResponseDto studentResp = studentService.getStudent(id);
        if(studentResp == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentResp);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetResponseDto>> getAllStudent(){
        List<GetResponseDto> studentList = studentService.getAllStudent();
        if(studentList == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentList);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateResponseDto> updateStudent(@RequestParam Long id,
                                                 @RequestBody UpdateRequestDto studentReq){
        UpdateResponseDto studentResp = studentService.updateStudent(id,studentReq);
        if(studentResp == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentResp);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
        Boolean isDeleted = studentService.deleteStudentRecord(id);
        if (!isDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record Deleted Successfully");
    }
    @PatchMapping("/delete-soft")
    public ResponseEntity<String> deleteStudentSoftly(@RequestParam Long id){
        Boolean isDeleted = studentService.deleteStudentSoftly(id);
        if (!isDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record Deleted Softly");
    }
}
