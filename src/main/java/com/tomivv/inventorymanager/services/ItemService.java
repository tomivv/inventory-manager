package com.tomivv.inventorymanager.services;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import com.tomivv.inventorymanager.dao.ItemRepository;
import com.tomivv.inventorymanager.entity.Item;
import com.tomivv.inventorymanager.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void addNewItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public void updateItem(Item item) {
        Item fetchedItem = itemRepository.findById(item.getId())
            .orElseThrow(() -> new NotFoundException("Didn't find item with id: " + item.getId()));

        if(item.getName().length() > 0 && !Objects.equals(item.getName(), fetchedItem.getName())) {
            fetchedItem.setName(item.getName());
        }
        if(item.getLocation().length() > 0 && !Objects.equals(item.getLocation(), fetchedItem.getLocation())) {
            fetchedItem.setLocation(item.getLocation());
        }
        if(item.getWeight() >= 0 && !Objects.equals(item.getWeight(), fetchedItem.getWeight())) {
            fetchedItem.setWeight(item.getWeight());
        }
        itemRepository.save(fetchedItem);
    }
}
