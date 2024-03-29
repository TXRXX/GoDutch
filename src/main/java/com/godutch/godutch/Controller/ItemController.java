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
    //เป็น annotation ที่ทำให้เราสามารถ 'Inject' object ขึ้นมาใช้งานได้ โดย object ดังกล่าวจะต้องถูกประกาศไว้ก่อนหน้า
    //และเป็น object ที่มีความเป็น Spring หรือมีชื่ออย่างเป็นทางการว่า "Spring Bean"
    private DutchRepository DutchRepo;
    @Autowired
    private ItemRepository itemRepo;
    @Autowired
    private SequenceGeneratorService service;

    /*
    * ทุกครั้งที่ create-dutch.html ถูกเปิด
    * สร้าง List Item เก็บข้อมูล Item ทั้งหมดในฐานข้อมูล (collection item)
    * ส่ง api ไปยังหน้า create-dutch (itemList) เพือที่จะแสดงข้อมูล item ทั้งหมด
    */
    @RequestMapping("/create-dutch") //function show list item in create-dutch
    public String getItemList(Model model){
//        System.out.println("Test in ItemList");
        List<Item> itemList = itemRepo.findAll();
        model.addAttribute("itemList",itemList);
        return "create-dutch";
    }

    /*
    * ใช้เพื่อเพิ่ม item เข้าไปในฐานข้อมูล (collection item) โดยรับข้อมูลมาจากหน้าเว็บ create-dutch.html
    */
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

    /*
    * ใช้ลบข้อมูล item ในฐานข้อมูล (collection item) ผ่าน id
    */
    @GetMapping("/delete_item/{id}")
    public String deleteItem(@PathVariable("id") long id, Model model){
        Item item = itemRepo.findById((int) id).orElseThrow(() -> new IllegalArgumentException("Invalid item id"+id));
        itemRepo.delete(item);
        return "redirect:/create-dutch";
    }

    //function update item in edit-dutch
    /*
    * ใช้ update item ในหน้า edit-dutch.html
    * */
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
    /*ใช้ลบข้อมูล item ในหน้า edit-dutch */
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
