package chargingstation;

public class PackDeliveryCanceled extends AbstractEvent {

    private Long id;
    private Integer packQty;
    private String packOrderType;
    private Long orderId;

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getPackQty() {
        return packQty;
    }

    public void setPackQty(Integer packQty) {
        this.packQty = packQty;
    }
    public String getOrderPackType() {
        return packOrderType;
    }

    public void setOrderPackType(String packOrderType) {
        this.packOrderType = packOrderType;
    }
}