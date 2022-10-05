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
    /*
    * ทุกครั้งที่เปิดหน้าเว็บ showIndex จะทำงาน โดยไปดึงข้อมูล Dutch ทั้งมหดในฐานข้อมูล (collection dutch)
    * มาเก็บเป็น List แล้วทำ api ส่งไปหน้าเว็บ เพื่อให้หน้าเว็บแสดงรายชื่อ Topic ของ dutch
    * */
    @RequestMapping("/")
    public String showIndex(Model model) {
        List<Dutch> dutchList = DutchRepo.findAll();
        model.addAttribute("dutchList",dutchList); //send api dutchList
        return "home";
    }

//    method save data to database and Delete all Item in database
    /*
    * จะถูกเรียกใช้ในหน้า create-dutch.html โดยหลังจากที่ user เพิ่ม item จนครบแล้ว
    * จะใส่ชื่อของ Topic แล้วกดปุ่ม CREATE ซึ่ง addDutch() จะถูกเรียกใช้ นำข้อมูล Item ใน collection item และ topic ที่อยู่หน้าเว็บ
    * มาเก็บไว้เป็น object Dutch แล้วบันทึกลงฐานข้อมูล (collection dutch) พร้อมลบข้อมูล Item ในฐานข้อมูล(collection item) item ให้หมด
    * */
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
    /*
    * ใช้เพื่อแสดงหน้า edit-dutch แต่ละ id ของ Dutch โดยค้นหาข้อมูล dutch ผ่าน id ที่รับมาจากหน้าเว็บ
    * แล้วส่งข้อมูลที่ค้นหาไปยังหน้า edit-dutch
    * */
    @GetMapping("/edit-dutch/{id}")
    public String editDutch(@PathVariable("id") int id,Model model){
        Dutch dutch = DutchRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dutch id: "+ id));
        model.addAttribute("dutch",dutch); //send api dutch
        return "edit-dutch";
    }

    //function go to calculator
    /* ใช้คำนวณหาจำนวนของแต่ละ member ต้องจ่าย ในหน้า receipt.html
    * จะถูกเรียกใช้ในหน้า receipt.html โดยเมื่อ กด Calculate
    * ทำงานโดยค้นหาข้อมูล dutch ผ่าน id ใน collection dutch
    * สร้าง object Receipt โดยส่ง dutch เข้าไป แล้วเรียกใช้ method calculate()
    * ส่ง api ออกไป (dutch และ receipt)
    * */
    @GetMapping("/receipt/{id}")
    public String calculate(@PathVariable("id") int id, Model model) {
        Dutch dutch = DutchRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dutch id: "+ id));
        Receipt receipt = new Receipt(dutch); //create object Receipt
        receipt.calculate();
        model.addAttribute("dutch",dutch); //send api dutch
        model.addAttribute("receipt",receipt); //send api receipt
        return "receipt";
    }
}
