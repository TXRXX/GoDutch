package com.godutch.godutch.Controller;

import com.godutch.godutch.Repository.DutchRepository;
import com.godutch.godutch.model.Dutch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class DutchController {
    @Autowired
    private DutchRepository repository;

    @RequestMapping("/create-dutch")
    public String showDutch() {return "create-dutch";}

//    method save data to database (create)
    @PostMapping("/create-dutch")
    public String addDutch(@Validated Dutch dutch, BindingResult result){
        if(result.hasErrors()){
            return "create-dutch";
        }
        repository.save(dutch);
        return "redirect:/";
    }

}
