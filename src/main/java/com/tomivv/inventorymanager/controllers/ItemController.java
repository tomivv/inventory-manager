package com.tomivv.inventorymanager.controllers;

import java.util.List;

import com.tomivv.inventorymanager.entity.Item;
import com.tomivv.inventorymanager.services.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    @GetMapping()
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @PostMapping
    public void addNewItem(@RequestBody Item item) {
        itemService.addNewItem(item);
    }

    @PutMapping
    public void updateItem(@RequestBody Item item) {
        itemService.updateItem(item);
    }

    @DeleteMapping
    public void deleteById(@RequestBody Long id) {
        System.out.println(id);
        itemService.deleteItemById(id);
    }
}
