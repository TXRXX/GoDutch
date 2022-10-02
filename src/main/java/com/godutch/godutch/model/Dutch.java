package com.godutch.godutch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dutch {

    @Transient
    public static final String SEQUENCE_NAME="dutchID";
    @Id
    private int id;
    private String topic;
    private List<Item> itemList = new ArrayList<>();

    private List<Member> member = new ArrayList<>();

    public List<Member> getMember() {
        return member;
    }

    public void setMember(List<Member> member) {
        this.member = member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void addItem(Item item){this.itemList.add(item);}

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String toString(){
        for(int i=0;i<itemList.size();i++){
            System.out.println(itemList.get(i));
        }
        return "id : "+ getId()+ ", topic : "+getTopic();
    }


}