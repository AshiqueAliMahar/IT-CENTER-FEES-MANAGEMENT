package Ali.ashique.ITCENTERFEESMANAGEMENT.command;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
public class FeeCommand {
    private long id;
    private Date payDate;

    @NotNull
    @NumberFormat
    @Min(value = 1000)
    private long amount;
}
