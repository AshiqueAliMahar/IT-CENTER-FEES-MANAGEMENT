package Ali.ashique.ITCENTERFEESMANAGEMENT.command;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class UserCommand {
    private String email;
    private String name;
    private String password;
    private String cnic;
    private byte[] pic;
    private List<StudentCommand> students = new LinkedList<>();
}
