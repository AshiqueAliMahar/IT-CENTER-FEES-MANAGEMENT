package Ali.ashique.ITCENTERFEESMANAGEMENT.models;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@Slf4j
@ToString(exclude = {"course"})
public class Batch {
    @Id
    private String batchName;
    private Time startTime;
    private Time endTime;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    private Course course;
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL)
    private List<Student> students = new LinkedList<>();
}
