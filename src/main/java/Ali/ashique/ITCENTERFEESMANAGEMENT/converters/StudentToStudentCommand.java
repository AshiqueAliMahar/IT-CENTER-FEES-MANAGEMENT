package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.StudentCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Student;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudentToStudentCommand implements Converter<Student, StudentCommand> {
    private FeeToFeeCommand feeToFeeCommand;

    @Override
    public StudentCommand convert(Student student) {
        StudentCommand studentCommand = new StudentCommand();
        studentCommand.setAdmissionDate(student.getAdmissionDate());
        studentCommand.setDob(student.getDob());
        studentCommand.setName(student.getName());
        studentCommand.setRollNo(student.getRollNo());
        studentCommand.setFeeCommands(student.getFees().stream().map(fee -> feeToFeeCommand.convert(fee)).collect(Collectors.toList()));
        return studentCommand;
    }
}
