package com.godutch.godutch.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Receipt {
    private Dutch dutch;
    private  HashMap<String,Double> priceShare; //เก็บ HashMap ราคา Item ที่หารออกมา
    private List<Member> memberList; // เก็บ List<Member> ที่คำนวณ price ที่แต่ละคนต้องจ่ายออกมา

    private Double totalPrice; // เก็บ จำนวนเงินทั้งหมดที่ต้องจ่าย

    //constructor
    public Receipt(Dutch dutch) {
        this.dutch = dutch;
    }

    public Dutch getDutch() {
        return dutch;
    }

    public void setDutch(Dutch dutch) {
        this.dutch = dutch;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    //method : calculate total price all item in Dutch.itemList
    /* สร้าง List<Item> ชื่อ itemList  โดยนำเอา list Item ทั้งหมดของ dutch ออกมา
    * สร้างตัวแปร total ชนิด double มาเก็บราคาของ item ทั้งหมดที่อยู่ใน itemList
    * แล้วนำไปเก็บไว้ในฟิลด์ totalPrice
    * */
    public void setTotalPrice(){
        List<Item> itemList = dutch.getItemList();
        double total = 0.0;
        for(Item i:itemList){
//            System.out.println("Name : "+ i.getItem_name() +"price: "+i.getPrice());
            total = total + i.getPrice();
        }
//        System.out.println("total : "+ total);
        this.totalPrice = total;
    }

    //method : return all item each member eat
    /* เริ่มจากสร้าง list มาเก็บ String ตั้งชื่อ ListItemEat เพื่อเก็บข้อมูลของอาหารแต่ละคนกิน
    *  loop ข้อมูลกับ ข้อมูล List<member> ใน dutch ดึงผ่าน method getMember
    *  เอาข้อมูล List<String> itemEat ของแต่ละ member มารวมเป็น list เดียวกัน
    */
    public List<String> itemMember(){
        List<String> listItemEat = new ArrayList<>();
        for (Member value : dutch.getMember()) {
            List<String> itemEat = value.getItemEat();
            listItemEat.addAll(itemEat);
        }
        return listItemEat; // list all item each member eat
    }
    //method : create Hashmap itemList
    /* สร้าง  HashMap<String,Double> ชื่อ itemMap เพื่อสร้าง hashMap ของ Item
    *  loop ข้อมูลกับ item แต่ละตัวที่เอามาจาก dutch.getItemList()
    *  map ข้อมูล โดยเอาชื่อ item ใน itemList แต่ละตัวมาสร้างเป็น key โดยกำหนด value = 0.0
    * */
    public  HashMap<String,Double>  createHash(){
        HashMap<String,Double> itemMap = new HashMap<String,Double>();
        List<Item> itemList = dutch.getItemList();
        for (Item item : itemList){
            itemMap.put(item.getItem_name(),0.0);
        }
//        System.out.println(itemMap);
        return itemMap;
    }

    //method : count item each member eat
    /* เริ่มจากสร้าง hashMap<String, Integer> ชื่อ allItem เพื่อเก็บจำนวนรายการที่ member กิน
    * สร้าง List<String> itemMember เก็บข้อมูลรายการทั้งหมดที่ member กิน (เรียกใช้ method itemMember())
    * loop กับข้อมูลแต่ละใน itemMember จากนั้น update value เพิ่มที่ละ 1
//    */
    public HashMap<String,Double> checkItem(){
        HashMap<String,Double> allItem = createHash(); //call method createHash()
        List<String> itemMember = itemMember(); //call method itemMember()
//        System.out.println("key : "+allItem.keySet());
//        System.out.println("allItemMemberEat : "+ itemMember);
        for (String s : itemMember) {
            allItem.put(s, allItem.get(s) + 1.0);
        }
//        System.out.println("count : "+allItem);
        return allItem;
    }

    //method calculate price share
    /* HashMap<String,Double> ชื่อ priceShare  โดยนำ  hashMap ที่ได้นับจำนวนรายการที่กินไว้มาใส่
    * loop ข้อมูล กับ itemList ที่เอามาจาก dutch
    * อัพเดต hashMap โดยใช้ key เดิม แต่ value จำนวนราคาของ item / จำนวนคนที่กิน item นั้น (ค่า value เดิม)
    * */
    public void calPrice(){
        HashMap<String,Double> priceShare = checkItem(); //call method checkItem()
        List<Item> itemList = dutch.getItemList();
        for (Item item : itemList) {
//            System.out.println("Item name : " +item.getItem_name());
//            System.out.println("Item price : "+item.getPrice());
//            System.out.println("quantity : "+priceShare.get(item.getItem_name()));
            priceShare.put(item.getItem_name(),item.getPrice() / priceShare.get(item.getItem_name()));
//            System.out.println("Share Pay : "+ priceShare.get(item.getItem_name()));
//            System.out.println("---------");
        }
//        System.out.println("=====================");
//        System.out.println("share Dict : " + priceShare);
        this.priceShare = priceShare;
    }

    //method : calculate total price each member
    /* เป็น method ที่คำนวณว่า member แต่ละคนต้องจ่าย้งินคนละเท่าใด
    * loop ข้อมูล List<Member> ดึง member แต่ละคนออกมา
    * loop หาว่า member คนนั้นกินอะไรบ้าง ราคาเท่าใด ซึ่งหาได้จาก Hashmap ที่สร้างไว้  priceShare.get(ชื่อรายการที่กิน)
    * บวกค่า total ตามจำนวนราคาที่ต้องจ่าย
    * แล้วนำไปเก็บไว้ในตัวแปร price ที่อยู่ใน member แต่ละคน
    * */
    public void calculate(){
        setTotalPrice(); //call method setTotalPrice();
        calPrice(); //cal method calPrice()
        List<Member> members = dutch.getMember();
        System.out.println("total price : " +totalPrice);
        for(Member m : members){
            double total = 0.0;
            System.out.println("Member Name: " + m.getName());
            for(String s : m.getItemEat()){
                System.out.println("\t"+s +" \t "+ priceShare.get(s));
                total = total + priceShare.get(s);
            }
            m.setPrice(total);
            System.out.println("total : "+m.getPrice());
        }
        this.memberList = members;
    }
}
