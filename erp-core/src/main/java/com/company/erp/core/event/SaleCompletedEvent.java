package com.company.erp.core.event;

public class SaleCompletedEvent {
    private Long saleId;
    private Long productId;
    private int quantitySold;
    private double totalAmount;

    public SaleCompletedEvent() {
    }

    public SaleCompletedEvent(Long saleId, Long productId, int quantitySold, double totalAmount) {
        this.saleId = saleId;
        this.productId = productId;
        this.quantitySold = quantitySold;
        this.totalAmount = totalAmount;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
