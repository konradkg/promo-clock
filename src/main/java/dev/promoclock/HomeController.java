package dev.promoclock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/welcome/{name}")
    public String name(@PathVariable("name") String name, Model model) {
        model.addAttribute("imie", name);
        return "index";
    }
}
