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
    private final List<Item> itemList = new ArrayList<>();

    @RequestMapping("/create-dutch")
    public String showDutch() {
        return "create-dutch";
    }

    @GetMapping("/dutch") // ส่ง list Dutch ออกไป
    public List<Dutch> getDutch(){
        return DutchRepo.findAll();
    }


//    method save data to database (create)
    @PostMapping("/create-dutch")
    public String addDutch(@Validated Dutch dutch, BindingResult result){
//        if(result.hasErrors()){
//            return "create-dutch";
//        }
//        repository.save(dutch);
        return "redirect:/";
    }

    @PostMapping("/add-Item") //function add item to itemList (form addItem)
    public String addItem(@Validated Item item,BindingResult result,Model model){
        model.addAttribute("item",item);
        itemList.add(item);
        System.out.println("Add Item");
        return  "create-dutch";
    }
}
