package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.StudentCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Student;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudentCommandToStudent implements Converter<StudentCommand, Student> {


    private FeeCommandToFee feeCommandToFee;

    @Override
    public Student convert(StudentCommand studentCommand) {
        Student student = new Student();
        student.setAdmissionDate(studentCommand.getAdmissionDate());

        student.setDob(studentCommand.getDob());
        student.setName(studentCommand.getName());
        student.setRollNo(studentCommand.getRollNo());

        student.setFees(studentCommand.getFeeCommands().stream().map(fee -> feeCommandToFee.convert(fee)).collect(Collectors.toList()));
        return student;
    }
}
