package com.godutch.godutch.Controller;

import com.godutch.godutch.Repository.ItemRepository;
import com.godutch.godutch.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    private ItemRepository itemRepo;

    @RequestMapping("/create-dutch") //function show list item in create-dutch
    public String getItemList(Model model){
        System.out.println("Test in ItemList");
        List<Item> itemList = itemRepo.findAll();
        model.addAttribute("itemList",itemList);
        return "create-dutch";
    }

    @PostMapping("/add-Item") //function add item to database item
    public String addItem(@Validated Item item, BindingResult result, Model model){
        System.out.println("Add Item");
        if(result.hasErrors()){
            return "create-dutch";
        }
        itemRepo.save(item);
        return  "create-dutch";
    }
}
