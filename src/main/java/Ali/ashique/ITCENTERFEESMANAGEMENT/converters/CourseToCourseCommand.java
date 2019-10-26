package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.BatchCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.command.CourseCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Batch;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Course;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@AllArgsConstructor
public class CourseToCourseCommand implements Converter<Course, CourseCommand> {

    private BatchToBatchCommand batchToBatchCommand;

    @Override
    public CourseCommand convert(Course src) {
        CourseCommand target = new CourseCommand();
        target.setFees(src.getFees());
        target.setName(src.getName());
        target.setStartDate(src.getStartDate());
        if (src.getBatches() != null && src.getBatches().size() > 0) {
            List<BatchCommand> batchCommands = new LinkedList<>();
            for (Batch batch : src.getBatches()) {
                BatchCommand batchCommand = batchToBatchCommand.convert(batch);
                batchCommands.add(batchCommand);
            }
            target.setBatches(batchCommands);
        }
        return target;
    }
}
