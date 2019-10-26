package Ali.ashique.ITCENTERFEESMANAGEMENT.models;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    @NotBlank
    @Size(min = 4, max = 255)
    private String name;

    @NotNull
    @NumberFormat
    @Min(value = 1000)
    private int fees;
    private Date startDate;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Batch> batches = new LinkedList<>();

    public List<Batch> addBatch(Batch batch) {
        batches.add(batch);
        return batches;
    }
}
