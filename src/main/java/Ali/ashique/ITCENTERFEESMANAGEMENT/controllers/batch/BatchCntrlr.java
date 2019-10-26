package Ali.ashique.ITCENTERFEESMANAGEMENT.controllers.batch;

import Ali.ashique.ITCENTERFEESMANAGEMENT.models.User;
import Ali.ashique.ITCENTERFEESMANAGEMENT.repositories.CourseRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/batch")
@AllArgsConstructor
public class BatchCntrlr {
    private CourseRepo courseRepo;

    @GetMapping({"/", ""})
    public String viewBatches(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute(user);
        return "batches/batches";
    }
}
