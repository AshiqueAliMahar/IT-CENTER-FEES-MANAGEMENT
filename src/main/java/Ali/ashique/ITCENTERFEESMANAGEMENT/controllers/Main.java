package Ali.ashique.ITCENTERFEESMANAGEMENT.controllers;

import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Course;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Student;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.User;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.CourseRepo;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.StudentRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class Main {
    private CourseRepo courseRepo;
    private StudentRepo studentRepo;

    @GetMapping("/")
    public String students(Model model, @RequestParam(defaultValue = "1") String pageNo, @AuthenticationPrincipal User user) {
        int pageNoInt = pageNo.isEmpty() ? 1 : Integer.parseInt(pageNo);
        List<Course> courses = new LinkedList<>();
        Page<Student> students = studentRepo.findAll(PageRequest.of(pageNoInt - 1, 1));
        model.addAttribute("courses", courseRepo.findAll());
        model.addAttribute("students", students);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute(user);
        return "students/students";
    }
}