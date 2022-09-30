package com.godutch.godutch.Controller;

import com.godutch.godutch.Repository.DutchRepository;
import com.godutch.godutch.Repository.ItemRepository;
import com.godutch.godutch.Service.SequenceGeneratorService;
import com.godutch.godutch.model.Dutch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class DutchController {
    @Autowired
    private DutchRepository DutchRepo;

    @Autowired
    private ItemRepository ItemRepo;
    @Autowired
    private SequenceGeneratorService service;


//    method save data to database (create)
    @GetMapping("/add-dutch")
    public String addDutch(@Validated Dutch dutch, BindingResult result, Model model){
        if(result.hasErrors()){
            return "create-dutch";
        }
        dutch.setId(service.getSequenceNumber(Dutch.SEQUENCE_NAME));
        dutch.setItemList(ItemRepo.findAll());
        DutchRepo.save(dutch);
        System.out.println("Add dutch");
        return "redirect:/";
    }
}
