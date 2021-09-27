package chargingstation;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
// import java.util.List;
// import java.util.Date;

@Entity
@Table(name="MountPart_table")
public class MountPart {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String orderPackType;
    private Integer orderPackQty;
    private String orderStatus;
    private String carName;
    private String carNumber;
    private String phoneNumber;
    private Long stockId;
    private String stockPackStatus;

    @PostPersist
    public void onPostPersist(){
        MountCompleted mountCompleted = new MountCompleted();
        BeanUtils.copyProperties(this, mountCompleted);
        mountCompleted.setOrderStatus("MOUNT_COMPLETED");
        mountCompleted.publishAfterCommit();

        mountCompleted.saveJsonToPvc(mountCompleted.getOrderStatus(), mountCompleted.toJson());

        System.out.println("$$$$$ Management onPostPersist, MOUNT_COMPLETED  $$$$$");
        System.out.println("$$$$$ mountCompleted : " + mountCompleted.toJson() + "$$$$$");

    }
    @PostRemove
    public void onPostRemove(){
        MountCanceled mountCanceled = new MountCanceled();
        BeanUtils.copyProperties(this, mountCanceled);
        mountCanceled.setOrderStatus("MOUNT_CANCELED");
        mountCanceled.publishAfterCommit();

        mountCanceled.saveJsonToPvc(mountCanceled.getOrderStatus(), mountCanceled.toJson());

        System.out.println("$$$$$ Management onPostRemove, MOUNT_CANCELED  $$$$$");
        System.out.println("$$$$$ mountCanceled : " + mountCanceled.toJson() + "$$$$$");

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getOrderPackType() {
        return orderPackType;
    }

    public void setOrderPackType(String orderPackType) {
        this.orderPackType = orderPackType;
    }
    public Integer getOrderPackQty() {
        return orderPackQty;
    }

    public void setOrderPackQty(Integer orderPackQty) {
        this.orderPackQty = orderPackQty;
    }
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }
    public String getStockPackStatus() {
        return stockPackStatus;
    }

    public void setStockPackStatus(String stockPackStatus) {
        this.stockPackStatus = stockPackStatus;
    }




}