package Ali.ashique.ITCENTERFEESMANAGEMENT.converters;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.UserCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserToUserCommandDTO {
    UserCommand getTareget(User user);
}
