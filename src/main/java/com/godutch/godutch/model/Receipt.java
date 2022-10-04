package com.godutch.godutch.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Receipt {
    private Dutch dutch;

    public Receipt(Dutch dutch) {
        this.dutch = dutch;
    }

    public Dutch getDutch() {
        return dutch;
    }

    public void setDutch(Dutch dutch) {
        this.dutch = dutch;
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
            System.out.println("Item name : " +item.getItem_name());
            System.out.println("Item price : "+item.getPrice());
            System.out.println("quantity : "+priceShare.get(item.getItem_name()));
            priceShare.put(item.getItem_name(),item.getPrice() / priceShare.get(item.getItem_name()));
            System.out.println("Share Pay : "+ priceShare.get(item.getItem_name()));
            System.out.println("---------");
        }
        System.out.println("=====================");
        System.out.println("share Dict : " + priceShare);
    }
}
