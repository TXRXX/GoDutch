package com.godutch.godutch.Controller;

import com.godutch.godutch.Repository.DutchRepository;
import com.godutch.godutch.Repository.MemberRepository;
import com.godutch.godutch.Service.SequenceGeneratorService;
import com.godutch.godutch.model.Dutch;
import com.godutch.godutch.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {
    @Autowired
    //เป็น annotation ที่ทำให้เราสามารถ 'Inject' object ขึ้นมาใช้งานได้ โดย object ดังกล่าวจะต้องถูกประกาศไว้ก่อนหน้า
    //และเป็น object ที่มีความเป็น Spring หรือมีชื่ออย่างเป็นทางการว่า "Spring Bean"
    private DutchRepository DutchRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private SequenceGeneratorService service;

    //function show to page add-member{id}
    /*ใช้เปิดหน้า add-member เพื่อเพิ่ม member ลงในฐานข้อมูล (collection member)
    * ส่ง api ข้อมูล Dutch ผ่าน id
    * ค้นหา Member ทั้งหมดใน ฐานข้อมูล(collection member) เพื่อส่ง api ไปหน้า add-member.html ให้แสดงข้อมูล member ทั้งหมด
    * */
    @GetMapping("/edit-member/{id}")
    public String editMember(@PathVariable("id") int id, Model model){
        Dutch dutch = DutchRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dutch id: "+ id));
        model.addAttribute("dutch",dutch);
        List<Member> memberList = memberRepo.findAll();
        model.addAttribute("memberList",memberList);
        return "add-member";
    }

    //function add member to database
    /*เพื่ม member ลงในฐานข้อมูล (collection member)*/
    @PostMapping("/add-member{id}")
    public String addMember(@RequestParam(name="item") List<String> item, @RequestParam(name="name") String name){
        Member member = new Member();
        member.setId(service.getSequenceNumber(Member.SEQUENCE_NAME));
        member.setName(name);
        member.setItemEat(item);
        memberRepo.save(member);
        return "redirect:/edit-member/{id}";
    }
    //function delete member by id
    /*ลบ member ในฐานข้อมูล (collection member) ผ่าน id*/
    @GetMapping("/delete_member{id}/{memberId}")
    public String deleteItem(@PathVariable("memberId") int memberId,Model model){
        Member member = memberRepo.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member id"+memberId));
        memberRepo.delete(member);
        return "redirect:/edit-member/{id}";
    }

    //function save member to Dutch by id
    /*เมื่อกด save ในหน้า add member จะข้อมูล member ทั้งหมดในฐานข้อมูล (collection member)
    * ไปใส่ใน Dutch ผ่าน id ลงในฐานข้อมูล (collection dutch)
    * จากนั้นลบข้อมูลทั้งหมดในฐานข้อมูล (collection member) และกลับไปยัง edit-dutch.html ผ่าน id
    */
    @GetMapping("/save-member{id}")
    public String saveMember(@PathVariable("id") int id){
        Dutch dutch = DutchRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dutch id: "+ id));
//        System.out.println(dutch.getId());
        dutch.setMember(memberRepo.findAll());
        DutchRepo.save(dutch);
        memberRepo.deleteAll();
        return "redirect:/edit-dutch/{id}";
    }

    //function delete Member in edit-dutch by id
    /*ลบข้อมูล member ในหน้า edit-dutch*/
    @GetMapping("/delete_member-dutch/{id}/{memberId}")
    public String deleteMemberInDutch(@PathVariable("id") int id, @PathVariable("memberId") int memberId){
        Dutch dutch = DutchRepo.findById((int) id).orElseThrow(() -> new IllegalArgumentException("Invalid item id"+id));
        List<Member> memberList = dutch.getMember();
        Member member = new Member();
        for(int i=0; i<memberList.size();i++){
            member = memberList.get(i);
            if(member.getId()==memberId){
                memberList.remove(member);
                dutch.setMember(memberList);
                DutchRepo.save(dutch);
                return "redirect:/edit-dutch/{id}";
            }
        }
        return "redirect:/edit-dutch/{id}";
    }
    //function back to edit-dutch ย้อนกลับไปหน้า edit-dutch.html
    @GetMapping("/back-edit-dutch/{id}")
    public String backToEditDutch(){
        return "redirect:/edit-dutch/{id}";
    }
}
