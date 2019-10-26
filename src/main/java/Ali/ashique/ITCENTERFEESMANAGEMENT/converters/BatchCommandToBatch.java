package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.BatchCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Batch;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class BatchCommandToBatch implements Converter<BatchCommand, Batch> {
    private StudentCommandToStudent studentCommandToStudent;

    @Override
    public Batch convert(BatchCommand src) {
        Batch target = new Batch();
        target.setBatchName(src.getBatchName());
        target.setEndDate(src.getEndDate());
        target.setStartDate(src.getStartDate());
        target.setStartTime(Time.valueOf(LocalTime.parse(src.getStartTime())));
        target.setEndTime(Time.valueOf(LocalTime.parse(src.getEndTime())));
        if (src.getStudents() != null && src.getStudents().size() > 0)
            target.setStudents(src.getStudents().stream().map(i -> studentCommandToStudent.convert(i)).collect(Collectors.toList()));
        return target;
    }

}