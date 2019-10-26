package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.UserCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {
    private StudentCommandToStudent studentCommandToStudent;

    @Override
    public User convert(UserCommand userCommand) {
        User user = new User();
        user.setCnic(userCommand.getCnic());
        user.setEmail(userCommand.getEmail());
        user.setName(userCommand.getName());
        user.setPassword(userCommand.getPassword());
        user.setPic(userCommand.getPic());
        user.setStudents(userCommand.getStudents().stream().map(studentCommand -> studentCommandToStudent.convert(studentCommand)).collect(Collectors.toList()));
        return user;
    }

    @Autowired
    public void setStudentCommandToStudent(StudentCommandToStudent studentCommandToStudent) {
        this.studentCommandToStudent = studentCommandToStudent;
    }
}
