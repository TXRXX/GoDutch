package com.godutch.godutch.Controller;

import com.godutch.godutch.Repository.DutchRepository;
import com.godutch.godutch.Repository.ItemRepository;
import com.godutch.godutch.Service.SequenceGeneratorService;
import com.godutch.godutch.model.Dutch;
import com.godutch.godutch.model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class DutchController {
    @Autowired
    private DutchRepository DutchRepo;


    @Autowired
    private ItemRepository ItemRepo;
    @Autowired
    private SequenceGeneratorService service;


    //show home Index
    @RequestMapping("/")
    public String showIndex(Model model) {
        List<Dutch> dutchList = DutchRepo.findAll();
        model.addAttribute("dutchList",dutchList);
        return "home";
    }

//    method save data to database and Delete all Item in database
    @PostMapping("/add-dutch")
    public String addDutch(@Validated Dutch dutch, BindingResult result, Model model){
        if(result.hasErrors()){
            return "create-dutch";
        }
        dutch.setId(service.getSequenceNumber(Dutch.SEQUENCE_NAME));
        dutch.setItemList(ItemRepo.findAll());
        DutchRepo.save(dutch);
//        System.out.println("Add dutch");
        ItemRepo.deleteAll();
        return "redirect:/";
    }

    //function to show EditDutch by id
    @GetMapping("/edit-dutch/{id}")
    public String editDutch(@PathVariable("id") int id,Model model){
        Dutch dutch = DutchRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dutch id: "+ id));
        model.addAttribute("dutch",dutch);
        return "edit-dutch";
    }

    //function go to calculator
    @GetMapping("/receipt/{id}")
    public String calculate(@PathVariable("id") int id, Model model) {
        Dutch dutch = DutchRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dutch id: "+ id));
        Receipt receipt = new Receipt(dutch);
        receipt.calPrice();
        model.addAttribute("dutch",dutch);
        return "receipt";
    }
}
