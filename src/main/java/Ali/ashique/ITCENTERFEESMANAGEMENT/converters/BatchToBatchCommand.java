package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.BatchCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Batch;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BatchToBatchCommand implements Converter<Batch, BatchCommand> {
    private StudentToStudentCommand studentToStudentCommand;

    @Override
    public BatchCommand convert(Batch src) {
        BatchCommand target = new BatchCommand();
        target.setBatchName(src.getBatchName());
        target.setEndDate(src.getEndDate());
        target.setStartDate(src.getStartDate());
        target.setStartTime(src.getStartTime().toString());
        target.setEndTime(src.getEndTime().toString());
        if (src.getStudents().size() > 0)
            target.setStudents(src.getStudents().stream().map(i -> studentToStudentCommand.convert(i)).collect(Collectors.toList()));
        return target;
    }
}
