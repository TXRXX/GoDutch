package com.godutch.godutch.Controller;

import com.godutch.godutch.Repository.DutchRepository;
import com.godutch.godutch.Repository.MemberRepository;
import com.godutch.godutch.Service.SequenceGeneratorService;
import com.godutch.godutch.model.Dutch;
import com.godutch.godutch.model.Item;
import com.godutch.godutch.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {
    @Autowired
    private DutchRepository DutchRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private SequenceGeneratorService service;

    //function show to page add-member{id}
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
    @GetMapping("/delete_member{id}/{memberId}")
    public String deleteItem(@PathVariable("memberId") int memberId,Model model){
        Member member = memberRepo.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member id"+memberId));
        memberRepo.delete(member);
        return "redirect:/edit-member/{id}";
    }

}
