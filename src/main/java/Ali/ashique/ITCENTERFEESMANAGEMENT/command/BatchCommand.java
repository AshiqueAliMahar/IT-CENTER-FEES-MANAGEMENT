package Ali.ashique.ITCENTERFEESMANAGEMENT.command;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class BatchCommand {
    @NotNull
    @NotEmpty(message = "It Can't be empty")
    @Size(min = 5, message = "At Least 5 Characters In Batch Name")
    private String batchName;
    private String startTime;
    private String endTime;
    private Date startDate;
    private Date endDate;
    private List<StudentCommand> students = new LinkedList<>();
}
