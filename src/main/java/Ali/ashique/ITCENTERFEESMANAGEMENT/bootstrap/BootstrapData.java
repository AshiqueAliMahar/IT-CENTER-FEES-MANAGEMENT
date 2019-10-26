package Ali.ashique.ITCENTERFEESMANAGEMENT.bootstrap;

import Ali.ashique.ITCENTERFEESMANAGEMENT.models.*;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.CourseRepo;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepo userRepo;
    private CourseRepo courseRepo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        User ashique = new User();
        ashique.setEmail("a@gmail.com");
        ashique.setName("ashique Ali");
        ashique.setPassword(new BCryptPasswordEncoder().encode("123"));
        ashique.setCnic("42401-8477229-3");

        Course course = new Course();
        course.setName("Java");
        course.setFees(10000);
        course.setStartDate(new Date(new java.util.Date().getTime()));

        Batch batch = new Batch();
        batch.setBatchName("Java Batch");
        batch.setCourse(course);
        batch.setEndDate(new Date(new java.util.Date().getTime()));
        batch.setStartDate(new Date(new java.util.Date().getTime()));
        batch.setEndTime(new Time(new java.util.Date().getTime()));
        batch.setStartTime(new Time(new java.util.Date().getTime()));
        course.setBatches(Stream.of(batch).collect(Collectors.toList()));


        Student afaque = new Student();
        afaque.setAdmissionDate(new Date(new java.util.Date().getTime()));
        afaque.setBatch(batch);
        afaque.setDob(Date.valueOf(LocalDate.of(1998, 1, 1)));
        afaque.setFees(Stream.of(new Fee(0, Date.valueOf(LocalDate.now()), 1200, afaque)).collect(Collectors.toList()));
        afaque.setName("Afaque Patoli");
        afaque.setRollNo("16CS13");
        afaque.setUser(ashique);
        ashique.setStudents(Stream.of(afaque).collect(Collectors.toList()));
        courseRepo.save(course);
        userRepo.save(ashique);


    }
}
