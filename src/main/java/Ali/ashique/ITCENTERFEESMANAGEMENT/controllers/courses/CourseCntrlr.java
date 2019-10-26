package Ali.ashique.ITCENTERFEESMANAGEMENT.controllers.courses;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.BatchCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.converters.BatchCommandToBatch;
import Ali.ashique.ITCENTERFEESMANAGEMENT.converters.BatchToBatchCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.converters.CourseToCourseCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Batch;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Course;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.User;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.CourseRepo;
import Ali.ashique.ITCENTERFEESMANAGEMENT.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/courses")
@Controller
@AllArgsConstructor
@Slf4j
public class CourseCntrlr {
    private CourseRepo courseRepo;
    private BatchCommandToBatch batchCommandToBatch;
    private BatchToBatchCommand batchToBatchCommand;
    private CourseToCourseCommand courseToCourseCommand;
    private CourseService courseService;

    @GetMapping({"/", ""})
    public String getCourse(Model model, @RequestParam(defaultValue = "0") int pageNo, @AuthenticationPrincipal User user) {
        model.addAttribute("courses", courseRepo.findAll(PageRequest.of(pageNo, 10)));
        model.addAttribute(user);
        return "courses/courses";
    }


    @PostMapping("/add-course")
    public String addCoursePost(Model model, @Valid @ModelAttribute("course") Course course, Errors errors, @AuthenticationPrincipal User user) {
        System.out.println("Error in Add-course" + errors.hasErrors());
        if (errors.hasErrors()) {
            System.out.println("Error in Add-course");
            log.error("Error in add-course");
            model.addAttribute(user);
            return "courses/add-course";
        }
        Course course1 = courseRepo.save(course);
        return "redirect:/courses/view-course/" + course1.getName();
    }

    @GetMapping("/add-course")
    public String addCourse(Model model, @AuthenticationPrincipal User user) {
        System.out.println("Error in Add-course get");
        model.addAttribute(user);
        model.addAttribute("course", new Course());
        return "courses/add-course";
    }

    @GetMapping("/view-course/{name}")
    public String viewCourse(@PathVariable("name") String courseName, Model model, @AuthenticationPrincipal User user) {
        Optional<Course> course = courseRepo.findById(courseName);
        model.addAttribute(user);
        model.addAttribute("course", course.get());
        return "courses/view-course";
    }

    @GetMapping("/edit-course/{courseName}")
    public String editCourse(Model model, @PathVariable String courseName, @AuthenticationPrincipal User user) {
        Optional<Course> course = courseRepo.findById(courseName);
        model.addAttribute(course.get());
        model.addAttribute(user);
        return "courses/edit-course";
    }

    @GetMapping("/del-course/{courseName}")
    public String delCourse(Model model, @PathVariable String courseName, @AuthenticationPrincipal User user) {
        courseRepo.deleteById(courseName);
        model.addAttribute(user);
        return "redirect:/courses/?msg=" + courseName + " Course Deleted";
    }

    @GetMapping("/{courseName}/add-batch")
    public String addBatch(Model model, @PathVariable String courseName, @AuthenticationPrincipal User user) {
        Optional<Course> course = courseRepo.findById(courseName);
        model.addAttribute(course.get());
        model.addAttribute(new BatchCommand());
        model.addAttribute(user);
        return "courses/batches/add-batch";
    }

    @PostMapping("/{courseName}/add-batch")
    public String addBatchPost(@PathVariable String courseName, @ModelAttribute @Valid BatchCommand batchCommand, Errors errors, Model model, @AuthenticationPrincipal User user) {
        Course course = courseRepo.findById(courseName).get();

        if (errors.hasErrors()) {
            model.addAttribute(course);
            model.addAttribute(user);
            return "courses/batches/add-batch";
        }

        Batch batch = batchCommandToBatch.convert(batchCommand);
        batch.setCourse(course);
        List<Batch> batches = course.getBatches();
        course.getBatches().stream().anyMatch(batch1 -> {
            boolean isMatched = batch1.getBatchName().equals(batch.getBatchName());
            if (isMatched) {
                batches.remove(batch1);
                batch.setStudents(batch1.getStudents());
            }
            return isMatched;
        });
        batches.add(batch);
        course.setBatches(batches);
        Course save = courseRepo.save(course);
        return "redirect:/courses/view-course/" + save.getName();
    }

    @GetMapping("/{courseName}/batches/{batchName}")
    public String getBatch(Model model, @PathVariable String courseName, @PathVariable String batchName, @AuthenticationPrincipal User user) {
        Optional<Course> course = courseRepo.findById(courseName);
        model.addAttribute(course.get());
        model.addAttribute(user);
        List<Batch> batches = course.get().getBatches();
        batches.stream().anyMatch(batch -> {
            boolean isMatched = batch.getBatchName().equals(batchName);
            if (isMatched) model.addAttribute(batchToBatchCommand.convert(batch));
            return isMatched;
        });
        return "courses/batches/edit-batch";
    }

    @GetMapping("/{courseName}/del-batch/{batchName}")
    public String delBatch(@PathVariable String courseName, @PathVariable String batchName, @AuthenticationPrincipal User user, Model model) {
        courseService.deleteBatch(courseName, batchName);
        model.addAttribute(user);
        return "redirect:/courses/view-course/" + courseName;
    }
}
