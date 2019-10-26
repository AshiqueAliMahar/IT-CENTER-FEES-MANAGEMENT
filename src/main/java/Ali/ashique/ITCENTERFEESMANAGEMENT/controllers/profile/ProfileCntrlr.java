package Ali.ashique.ITCENTERFEESMANAGEMENT.controllers.profile;

import Ali.ashique.ITCENTERFEESMANAGEMENT.command.UserCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.converters.UserCommandToUser;
import Ali.ashique.ITCENTERFEESMANAGEMENT.converters.UserToUserCommand;
import Ali.ashique.ITCENTERFEESMANAGEMENT.models.User;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Controller()
@RequestMapping("/profile")
@AllArgsConstructor
public class ProfileCntrlr {
    private UserRepo userRepo;
    private UserToUserCommand userToUserCommand;
    private UserCommandToUser userCommandToUser;

    @GetMapping("/{email}")
    public String getProfile(@PathVariable String email, Model model, @AuthenticationPrincipal User user) {
        Optional<User> optionalUser = userRepo.findById(email);
        UserCommand userCommand = userToUserCommand.convert(optionalUser.get());
        model.addAttribute(userCommand);
        model.addAttribute(user);
        return "profile/Profile";
    }

    @GetMapping("/{email}/pic")
    public void getImage(@PathVariable String email, HttpServletResponse response) throws IOException {
        User user = userRepo.findById(email).get();
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = response.getOutputStream();
        InputStream inputStream = new ByteArrayInputStream(user.getPic());
        IOUtils.copy(inputStream, outputStream);
    }

    @PostMapping("/{email}")
    public String saveProfile(@ModelAttribute UserCommand userCommand, @PathVariable String email) {
        User user = userCommandToUser.convert(userCommand);
        userRepo.findById(email).ifPresent(i -> user.setPic(i.getPic()));
        User save = userRepo.save(user);
        return "redirect:/profile/" + save.getEmail();
    }

    @GetMapping("/{email}/changePic")
    public String changeProfile(Model model, @PathVariable String email, @AuthenticationPrincipal User user) {
        model.addAttribute("email", email);
        model.addAttribute(user);
        return "profile/change pic";
    }

    @PostMapping("/{email}/changePic")
    public String changeProfilePost(Model model, @RequestParam("email") String email, @RequestParam("image") MultipartFile image) throws IOException {
        InputStream inputStream = image.getInputStream();
        byte[] imageBytes = new byte[inputStream.available()];
        inputStream.read(imageBytes);
        User user = userRepo.findById(email).get();
        user.setPic(imageBytes);
        userRepo.save(user);
        return "redirect:/";
    }
}
