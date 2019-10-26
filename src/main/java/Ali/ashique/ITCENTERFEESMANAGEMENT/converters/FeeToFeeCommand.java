package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.FeeCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Fee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FeeToFeeCommand implements Converter<Fee, FeeCommand> {

    @Override
    public FeeCommand convert(Fee src) {
        FeeCommand target = new FeeCommand();
        target.setAmount(src.getAmount());
        target.setId(src.getId());
        target.setPayDate(src.getPayDate());
        return target;
    }
}
