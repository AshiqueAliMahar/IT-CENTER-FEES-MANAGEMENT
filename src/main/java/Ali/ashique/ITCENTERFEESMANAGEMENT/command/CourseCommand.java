package Ali.ashique.ITCENTERFEESMANAGEMENT.command;

import lombok.Data;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Data
public class CourseCommand {
    private String name;
    private int fees;
    private Date startDate;
    private List<BatchCommand> batches = new LinkedList<>();
}
