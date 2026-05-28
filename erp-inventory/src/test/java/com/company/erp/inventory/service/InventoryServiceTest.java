package com.company.erp.inventory.service;

import com.company.erp.core.exception.ResourceNotFoundException;
import com.company.erp.inventory.entity.Product;
import com.company.erp.inventory.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RedisTemplate<Object, Object> redisTemplate;

    @Mock
    private ValueOperations<Object, Object> valueOperations;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void testGetProduct_Success() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Laptop");
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        
        Product result = inventoryService.getProduct(1L);
        assertNotNull(result);
        assertEquals("Laptop", result.getName());
    }

    @Test
    public void testGetProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(ResourceNotFoundException.class, () -> {
            inventoryService.getProduct(1L);
        });
    }

    @Test
    public void testUpdateInventory_Success() throws ExecutionException, InterruptedException {
        Product p = new Product();
        p.setId(1L);
        p.setStockQuantity(50);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        when(productRepository.save(any(Product.class))).thenReturn(p);
        
        CompletableFuture<com.company.erp.inventory.dto.InventoryUpdateResult> future = inventoryService.updateInventory(1L, -10);
        com.company.erp.inventory.dto.InventoryUpdateResult result = future.get();
        
        assertEquals(1L, result.getProductId());
        assertEquals(50, result.getPreviousStock());
        assertEquals(40, result.getNewStock());
        assertEquals("SUCCESS", result.getStatus());
        
        verify(productRepository, times(1)).save(any(Product.class));
        verify(valueOperations, times(1)).set(eq("inventory:1"), eq(40), any());
    }

    @Test
    public void testUpdateInventory_InsufficientStock() {
        Product p = new Product();
        p.setId(1L);
        p.setStockQuantity(5);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        
        Exception ex = assertThrows(RuntimeException.class, () -> {
            inventoryService.updateInventory(1L, -10);
        });
        
        assertEquals("Insufficient stock available", ex.getMessage());
    }
}
