package pl.ppsoft.spring_boot_security_course.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/management/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = new ArrayList<>(Arrays.asList(
            new Student(1, "James Bond"),
            new Student(2, "Maria Jones"),
            new Student(3, "Anna Smith")));

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINEE')")
    public List<Student> getAllStudents() {
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public List<Student> registerNewStudent(@RequestBody Student student) {
        STUDENTS.add(student);

        return STUDENTS;
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public List<Student> deleteStudent(@PathVariable Integer studentId) {
        STUDENTS.removeIf(student -> Objects.equals(student.getStudentId(), studentId));

        return STUDENTS;
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public List<Student> updateStudent(@PathVariable Integer studentId, @RequestBody Student student) {
        for (Student s : STUDENTS) {
            if (s.getStudentId().equals(studentId)) {
                s.setStudentName(student.getStudentName());
            }
        }

        return STUDENTS;
    }
}
