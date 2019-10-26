package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.UserCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {
    private StudentToStudentCommand studentToStudentCommand;

    @Override
    public UserCommand convert(User user) {
        UserCommand userCommand = new UserCommand();
        if (user == null)
            throw new RuntimeException();
        userCommand.setCnic(user.getCnic());
        userCommand.setEmail(user.getEmail());
        userCommand.setName(user.getName());
        userCommand.setPassword(user.getPassword());
        userCommand.setPic(user.getPic());
        userCommand.setStudents(user.getStudents().stream().map(i -> studentToStudentCommand.convert(i)).collect(Collectors.toList()));
        return userCommand;
    }

    @Autowired
    public void setStudentToStudentCommand(StudentToStudentCommand studentToStudentCommand) {
        this.studentToStudentCommand = studentToStudentCommand;
    }
}
