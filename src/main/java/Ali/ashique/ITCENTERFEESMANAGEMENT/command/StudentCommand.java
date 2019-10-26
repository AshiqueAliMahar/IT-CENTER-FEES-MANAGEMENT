package Ali.ashique.ITCENTERFEESMANAGEMENT.command;

import lombok.Data;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class StudentCommand {
    private String rollNo;
    private String name;
    private Date dob;
    private Date admissionDate;
    private List<FeeCommand> feeCommands = new LinkedList<>();

    public long sumOfFees() {
        return feeCommands.stream().mapToLong(i -> i.getAmount()).sum();
    }
}
