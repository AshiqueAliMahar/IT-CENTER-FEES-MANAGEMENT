package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.FeeCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.Fee;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FeeCommandToFee implements Converter<FeeCommand, Fee> {
    @Override
    public Fee convert(FeeCommand src) {
        Fee target = new Fee();
        target.setAmount(src.getAmount());
        target.setId(src.getId());
        target.setPayDate(src.getPayDate());
        return target;
    }
}
