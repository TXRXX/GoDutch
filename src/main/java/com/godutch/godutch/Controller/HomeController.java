package com.godutch.godutch.Controller;

import com.godutch.godutch.Repository.DutchRepository;
import com.godutch.godutch.model.Dutch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private DutchRepository DutchRepo;

    @RequestMapping("/")
    public String showIndex(Model model) {
        List<Dutch> dutchList = DutchRepo.findAll();
        model.addAttribute("dutchList",dutchList);
        return "home";
    }
}
