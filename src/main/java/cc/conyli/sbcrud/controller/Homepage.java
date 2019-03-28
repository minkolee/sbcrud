package cc.conyli.sbcrud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Homepage {

    @GetMapping("/")
    public String showHomepage(Model model) {
        model.addAttribute("date", new java.util.Date());
        return "homepage";
    }
}
