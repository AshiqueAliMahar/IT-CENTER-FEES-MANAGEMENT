package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.CourseCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CourseCommandToCourse implements Converter<CourseCommand, Course> {
    private BatchCommandToBatch batchCommandToBatch;

    @Override
    public Course convert(CourseCommand src) {
        Course target = new Course();
        target.setFees(src.getFees());
        target.setName(src.getName());
        target.setStartDate(src.getStartDate());
        target.setBatches(src.getBatches().stream().map(batch -> batchCommandToBatch.convert(batch)).collect(Collectors.toList()));
        return target;
    }

    @Autowired
    public void setBatchCommandToBatch(BatchCommandToBatch batchCommandToBatch) {
        this.batchCommandToBatch = batchCommandToBatch;
    }
}
