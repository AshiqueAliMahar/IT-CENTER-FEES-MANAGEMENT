package Ali.ashique.ITCENTERFEESMANAGEMENT.controllers;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.FeeCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.converters.FeeCommandToFee;
import Ali.ashique.ITCENTERFEESMANAGEMENT.converters.FeeToFeeCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.converters.StudentToStudentCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.exception.NoSuchElementExceptions;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Fee;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Student;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.User;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.CourseRepo;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.StudentRepo;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.UserRepo;
import Ali.ashique.ITCENTERFEESMANAGEMENT.service.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@AllArgsConstructor
@Slf4j
public class StudentCntrlr {
    private CourseRepo courseRepo;
    private StudentRepo studentRepo;
    private final StudentToStudentCommand studentToStudentCommand;
    private final FeeToFeeCommand feeToFeeCommand;
    private FeeCommandToFee feeCommandToFee;
    private UserRepo userRepo;

    @GetMapping("/student/add-student")
    public String addStudent(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("courses", courseRepo.findAll());
        model.addAttribute("student", new Student());
        model.addAttribute(user);
        return "students/add-student";
    }

    @PostMapping("/student/add-student")
    public String addStudentPost(Model model, @ModelAttribute @Valid final Student student, Errors errors, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            model.addAttribute("courses", courseRepo.findAll());
            model.addAttribute(user);
            return "students/add-student";
        }
        student.setAdmissionDate(Date.valueOf(LocalDate.now()));
        student.setUser(userRepo.findById("a@gmail.com").get());
        Student student1 = studentRepo.save(student);
        model.addAttribute("student", student1);
        return "redirect:/students/view-student/" + student1.getRollNo();
    }

    @GetMapping("/students/view-student/{rollNo}")
    public String viewStudent(@PathVariable String rollNo, Model model, @AuthenticationPrincipal User user) {

        Optional<Student> student = studentRepo.findById(rollNo);
        if (!student.isPresent()) {
            throw new NoSuchElementExceptions("Not Found");
        }
        model.addAttribute(user);
        model.addAttribute("student", student.get());
        return "students/view-student";
    }

    @GetMapping("/students/edit-student/{rollNo}")
    public String editStudent(Model model, @PathVariable String rollNo, @AuthenticationPrincipal User user) {
        Optional<Student> student = studentRepo.findById(rollNo);
        model.addAttribute("courses", courseRepo.findAll());
        model.addAttribute("student", student.get());
        model.addAttribute(user);
        return "students/edit-student";
    }

    @PostMapping("/students/edit-student/{rollNo}")
    public String editStudentPost(Model model, @PathVariable String rollNo) {
        Optional<Student> student = studentRepo.findById(rollNo);
        model.addAttribute("courses", courseRepo.findAll());
        model.addAttribute("student", student.get());
        return "students/edit-student";
    }

    @GetMapping("/students/del-student/{rollNo}")
    public String delStudent(@PathVariable String rollNo, @AuthenticationPrincipal User user) {
        studentRepo.deleteById(rollNo);
        return "redirect:/?msg=deleted";
    }

    @GetMapping("/students/{rollNo}/add-fees")
    public String addFee(Model model, @PathVariable String rollNo, @AuthenticationPrincipal User user) {
        Student student = studentRepo.findById(rollNo).get();
        model.addAttribute(studentToStudentCommand.convert(student));
        model.addAttribute(new FeeCommand());
        model.addAttribute(user);
        return "students/fees/add-fee";
    }

    @GetMapping("/students/{rollNo}/edit-fees/{feeId}")
    public String addFee(Model model, @PathVariable String rollNo, @PathVariable String feeId, @AuthenticationPrincipal User user) {
        Optional<Student> optionalStudent = studentRepo.findById(rollNo);
        if (!StringUtil.isNumber(feeId)) {
            throw new NumberFormatException("Invalid Fees Id " + feeId);
        } else if (!optionalStudent.isPresent()) throw new NoSuchElementException("Student Not Found");

        model.addAttribute(studentToStudentCommand.convert(optionalStudent.get()));
        model.addAttribute(user);
        boolean b = optionalStudent.get().getFees().stream().anyMatch(fee -> {
            boolean isFound = fee.getId() == Long.valueOf(feeId);
            if (isFound) model.addAttribute(feeToFeeCommand.convert(fee));
            return isFound;
        });
        if (!b) throw new NoSuchElementException("Student Fee Not Found");
        return "students/fees/add-fee";
    }

    @PostMapping("/students/{rollNo}/add-fees")
    public String addFeePost(Model model, @PathVariable String rollNo, @ModelAttribute @Valid FeeCommand feeCommand, Errors errors, @AuthenticationPrincipal User user) {
        Student student = studentRepo.findById(rollNo).get();
        if (errors.hasErrors()) {
            model.addAttribute(studentToStudentCommand.convert(student));
            model.addAttribute(user);
            return "students/fees/add-fee";
        }
        feeCommand.setPayDate(Date.valueOf(LocalDate.now()));
        Fee fee = feeCommandToFee.convert(feeCommand);
        fee.setStudent(student);
        student.addFee(fee);

        Student save = studentRepo.save(student);
        return "redirect:/students/view-student/" + save.getRollNo();
    }

    @GetMapping("/students/{rollNo}/delFees/{feeId}")
    public String delFee(@PathVariable String rollNo, @PathVariable String feeId) {
        if (!StringUtil.isNumber(feeId)) {
            throw new NumberFormatException("Invalid Fees Id" + feeId);
        }
        Student student = studentRepo.findById(rollNo).get();
        student.getFees().stream().anyMatch(fee -> {
            boolean isFound = fee.getId() == Long.valueOf(feeId);

            if (isFound) {
                List<Fee> fees = student.getFees();
                fee.setStudent(null);
                fees.remove(fee);
                student.setFees(fees);
                studentRepo.save(student);
            }
            return isFound;
        });
        return "redirect:/students/view-student/" + rollNo;
    }

    @GetMapping("/students/page/{pageNo}")
    public String changePage(@PathVariable String pageNo, HttpServletRequest request) {
        request.setAttribute("pageNo", pageNo);
        return "forward:/";
    }

}
