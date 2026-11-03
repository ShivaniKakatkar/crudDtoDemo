package in.strikes.crudDtoDemo.dto;

import jakarta.validation.constraints.*;

public class RequestDto {

    @NotBlank(message = "Name can't be null/empty/blank")
    @Size(min = 2,max = 150,message = "Student name must be within 2 to 150 character long")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 18,message = "Student must be at least 18 years old")
    private Integer age;

    @NotBlank(message = "email can't be null/empty/blank")
    @Email(message = "Student email must be valid")
    private String email;

    @NotNull(message = "Roll no is required")
    private Integer rno;

    @NotNull(message = "Subject is required")
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRno() {
        return rno;
    }

    public void setRno(int rno) {
        this.rno = rno;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
