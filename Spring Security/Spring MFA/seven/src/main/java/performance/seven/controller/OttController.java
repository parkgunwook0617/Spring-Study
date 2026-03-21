package performance.seven.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OttController {
    @GetMapping("/ott/sent")
    public String sent() {
        return "sent";
    }

    @GetMapping("/ott")
    public String ott(Model model) {
        model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
        return "ott";
    }

    @GetMapping("/login/ott")
    public String loginOtt(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "loginOtt";
    }
}
