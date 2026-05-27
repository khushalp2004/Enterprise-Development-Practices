package com.company.erp.inventory.dto;
public class InventoryUpdateResult {
    private Long productId;
    private int oldStock;
    private int newStock;
    private String status;
    
    public InventoryUpdateResult(Long productId, int oldStock, int newStock, String status) {
        this.productId = productId;
        this.oldStock = oldStock;
        this.newStock = newStock;
        this.status = status;
    }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public int getOldStock() { return oldStock; }
    public void setOldStock(int oldStock) { this.oldStock = oldStock; }
    public int getNewStock() { return newStock; }
    public void setNewStock(int newStock) { this.newStock = newStock; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
