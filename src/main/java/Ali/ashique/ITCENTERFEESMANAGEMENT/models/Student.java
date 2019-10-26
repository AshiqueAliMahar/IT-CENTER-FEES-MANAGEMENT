package Ali.ashique.ITCENTERFEESMANAGEMENT.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user", "batch", "fees"})
public class Student {
    @Id
    @NotEmpty(message = "Enter Roll No")
    @Size(min = 6, max = 12, message = "Roll No must be of 6 to 12 characters")
    private String rollNo;
    @NotNull(message = "Name Shouldn't be null")
    @Size(min = 5, max = 255, message = "Name  Must be of 5 to 255 characters")
    private String name;
    @NotNull(message = "Dob Must Not be Empty")
    //@Pattern(regexp = "[0-3][0-9]-[0-1][0-9]-[0-9]{4}",message = "Date Of birth must be in dd/mm/yyyy format")
    private Date dob;
    private Date admissionDate;
    @ManyToOne
    private User user;
    @ManyToOne
    private Batch batch;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Fee> fees = new LinkedList<>();

    public long sumOfFees() {
        return fees.stream().mapToLong(i -> i.getAmount()).sum();
    }

    public void addFee(@NotNull Fee fee) {
        fees.add(fee);
    }
}
