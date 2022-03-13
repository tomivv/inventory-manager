package com.tomivv.inventorymanager;

import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.tomivv.inventorymanager.dao.ItemRepository;
import com.tomivv.inventorymanager.entity.Item;
import com.tomivv.inventorymanager.exception.NotFoundException;
import com.tomivv.inventorymanager.services.ItemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    private ItemService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ItemService(itemRepository);
    }
    
    @Test
    void canGetAllItems() {
        underTest.getItems();

        verify(itemRepository).findAll();
    }

    @Test
    void canAddNewItem() {
        Item item = new Item("TV", "room", 20D);

        underTest.addNewItem(item);

        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(itemArgumentCaptor.capture());
        Item capturedItem = itemArgumentCaptor.getValue();

        assertThat(capturedItem).isEqualTo(item);
    }

    @Test
    void canDeleteItem() {
        underTest.deleteItemById(anyLong());

        verify(itemRepository).deleteById(anyLong());
    }

    @Test
    void canUpdateItem() {
        Item item = new Item(1L, "TV", "room", 20D);
        given(itemRepository.findById(anyLong()))
            .willReturn(Optional.of(new Item(1L, "", "", 0D)));
    
        underTest.updateItem(item);

        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(itemArgumentCaptor.capture());
        Item capturedItem = itemArgumentCaptor.getValue();

        assertThat(capturedItem.getId()).isEqualTo(item.getId());
        assertThat(capturedItem.getName()).isEqualTo(item.getName());
        assertThat(capturedItem.getLocation()).isEqualTo(item.getLocation());
        assertThat(capturedItem.getWeight()).isEqualTo(item.getWeight());
    }

    @Test
    void willNotUpdateInvalidNameField() {
        Item item = new Item(1L, "", "room", 20D);
        given(itemRepository.findById(anyLong()))
            .willReturn(Optional.of(new Item(1L, "name", "location", 0D)));
    
        underTest.updateItem(item);

        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(itemArgumentCaptor.capture());
        Item capturedItem = itemArgumentCaptor.getValue();

        assertThat(capturedItem.getName()).isNotEqualTo(item.getName());
    }

    @Test
    void willNotUpdateInvalidLocationField() {
        Item item = new Item(1L, "TV", "", 20D);
        given(itemRepository.findById(anyLong()))
            .willReturn(Optional.of(new Item(1L, "name", "location", 0D)));
    
        underTest.updateItem(item);

        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(itemArgumentCaptor.capture());
        Item capturedItem = itemArgumentCaptor.getValue();

        assertThat(capturedItem.getLocation()).isNotEqualTo(item.getLocation());
    }

    @Test
    void willNotUpdateInvalidWeightField() {
        Item item = new Item(1L, "TV", "room", -20D);
        given(itemRepository.findById(anyLong()))
            .willReturn(Optional.of(new Item(1L, "name", "location", 0D)));
    
        underTest.updateItem(item);

        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(itemArgumentCaptor.capture());
        Item capturedItem = itemArgumentCaptor.getValue();

        assertThat(capturedItem.getWeight()).isNotEqualTo(item.getWeight());
    }

    @Test
    void willThrowNotFoundError() {
        Item item = new Item(1L, "TV", "room", -20D);
        given(itemRepository.findById(anyLong()))
            .willThrow(new NotFoundException("Didn't find item with id: " + item.getId()));

        assertThatThrownBy(() -> underTest.updateItem(item))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("Didn't find item with id: " + item.getId());
    }
}