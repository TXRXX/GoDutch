package com.godutch.godutch.Controller;

import com.godutch.godutch.Repository.DutchRepository;
import com.godutch.godutch.Repository.ItemRepository;
import com.godutch.godutch.Service.SequenceGeneratorService;
import com.godutch.godutch.model.Dutch;
import com.godutch.godutch.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    private DutchRepository DutchRepo;
    @Autowired
    private ItemRepository itemRepo;
    @Autowired
    private SequenceGeneratorService service;

    @RequestMapping("/create-dutch") //function show list item in create-dutch
    public String getItemList(Model model){
        System.out.println("Test in ItemList");
        List<Item> itemList = itemRepo.findAll();
        model.addAttribute("itemList",itemList);
        return "create-dutch";
    }

    @PostMapping("/add-Item") //function add item to database item
    public String addItem(@Validated Item item, BindingResult result, Model model){
        if(result.hasErrors()){
            return "create-dutch";
        }
        //generate sequence
       item.setId(service.getSequenceNumber(Item.SEQUENCE_NAME));
        itemRepo.save(item);
        System.out.println("Add Item");
        return  "redirect:/create-dutch";
    }
    @GetMapping("/delete_item/{id}")
    public String deleteItem(@PathVariable("id") long id, Model model){
        Item item = itemRepo.findById((int) id).orElseThrow(() -> new IllegalArgumentException("Invalid item id"+id));
        itemRepo.delete(item);
        return "redirect:/create-dutch";
    }

    //function update item in edit-dutch
    @PostMapping("/update-item-dutch/{id}")
    public String updateItem(@PathVariable("id") int id, @Validated Dutch dutch,BindingResult result, Model model){
        System.out.println(id);
        if(result.hasErrors()){
            dutch.setId(id);
            return "edit-dutch";
        }
//        DutchRepo.save(dutch);
        return "redirect:/edit-dutch/{id}";
    }
}
