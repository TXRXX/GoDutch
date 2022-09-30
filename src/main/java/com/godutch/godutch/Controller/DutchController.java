package com.godutch.godutch.Controller;

import com.godutch.godutch.Repository.DutchRepository;
import com.godutch.godutch.model.Dutch;
import com.godutch.godutch.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class DutchController {
    @Autowired
    private DutchRepository DutchRepo;


//    @GetMapping("/dutch") // ส่ง list Dutch ออกไป
//    public List<Dutch> getDutch(){
//        return DutchRepo.findAll();
//    }


//    method save data to database (create)
//    @PostMapping("/create-dutch")
//    public String addDutch(@Validated Dutch dutch, BindingResult result){
//        if(result.hasErrors()){
//            return "";
//        }
//        DutchRepo.save(dutch);
//        return "redirect:/";
//    }
//

}
