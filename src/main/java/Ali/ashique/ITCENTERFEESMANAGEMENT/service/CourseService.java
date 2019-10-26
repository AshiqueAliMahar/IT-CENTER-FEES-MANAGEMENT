package Ali.ashique.ITCENTERFEESMANAGEMENT.service;

import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Batch;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Course;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.CourseRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    private CourseRepo courseRepo;

    public void deleteBatch(String courseName, String batchName) {
        Course course = courseRepo.findById(courseName).get();
        List<Batch> batches = course.getBatches();
        batches.stream().anyMatch(
                batch -> {
                    boolean isMatched = batch.getBatchName().equals(batchName);
                    if (isMatched) {
                        batch.setCourse(null);
                        batches.remove(batch);
                    }
                    return isMatched;
                });
        course.setBatches(batches);
        courseRepo.save(course);
    }
}
