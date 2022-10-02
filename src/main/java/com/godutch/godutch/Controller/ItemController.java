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
import org.springframework.web.bind.annotation.*;


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
//        System.out.println("Test in ItemList");
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
//        System.out.println("Add Item");
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
    public String updateItem(@PathVariable("id") int id, @Validated Dutch dutch,@Validated Item item,BindingResult result, Model model){
//        System.out.println(id);
        if(result.hasErrors()){
            dutch.setId(id);
            return "edit-dutch";
        }
        item.setId(service.getSequenceNumber(Item.SEQUENCE_NAME));
        Dutch dutch1 = DutchRepo.findById((int) id).orElseThrow(() -> new IllegalArgumentException("Invalid item id"+id));
        dutch1.addItem(item);
//        System.out.println(dutch1);
       DutchRepo.save(dutch1);
//       System.out.println("update dutch id: "+id);
        return "redirect:/edit-dutch/{id}";
    }

    //function drop item in edit-dutch{id}
    @GetMapping("/delete_item-dutch/{id}/{ItemId}")
    public String deleteItemInDutch(@PathVariable("id") int id,@PathVariable("ItemId") int itemId,Model model){
        Dutch dutch = DutchRepo.findById((int) id).orElseThrow(() -> new IllegalArgumentException("Invalid item id"+id));
//        System.out.println(dutch.getId());
//        System.out.println(itemId);
        List<Item> itemList = dutch.getItemList();
        Item item = new Item();
        for(int i=0; i<itemList.size();i++){
            item = itemList.get(i);
            if(item.getId()==itemId){
//                System.out.println(item.getId()+" : "+itemId);
                itemList.remove(item);
                dutch.setItemList(itemList);
                DutchRepo.save(dutch);
                return "redirect:/edit-dutch/{id}";
            }
        }
        return "redirect:/edit-dutch/{id}";
    }
}
