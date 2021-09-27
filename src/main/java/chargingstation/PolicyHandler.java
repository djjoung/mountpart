package chargingstation;

import chargingstation.config.kafka.KafkaProcessor;

import org.springframework.beans.BeanUtils;
// import com.fasterxml.jackson.databind.DeserializationFeature;
// import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired 
    MountPartRepository mountPartRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverMountReqested_MountInfo(@Payload MountReqested mountReqested){
        // station으로 부터 MountInfo를 전달받는다.
        if(!mountReqested.validate()) return;
        
        System.out.println("$$$$$ listener MountInfo : " + mountReqested.toJson() + "$$$$$");

        if (mountReqested.getOrderStatus().equals("MOUNT_REQUESTED")){
            MountPart mountPart = new MountPart();
            BeanUtils.copyProperties(mountReqested, mountPart);
            mountPart.setOrderStatus("MOUNT_COMPLETED");
            mountPartRepository.save(mountPart);

            System.out.println("$$$$$ wheneverMountReqested_MountInfo $$$$$");
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverMountCancelRequested_MountCancelinfo(@Payload MountCancelRequested mountCancelRequested){
        // station으로 부터 MountCancel Info를 전달받는다.
        if(!mountCancelRequested.validate()) return;

        System.out.println("$$$$$ listener MountCancelinfo : " + mountCancelRequested.toJson() + " $$$$$");

        if (mountCancelRequested.getOrderStatus().equals("MOUNT_CANCEL_REQUESTED")){
            MountPart mountPart = mountPartRepository.findByOrderId(mountCancelRequested.getOrderId());
            if (mountPart != null){
                mountPart.setOrderStatus("MOUNT_CANCELED");
                mountPartRepository.delete(mountPart);

                System.out.println("$$$$$ wheneverMountCancelRequested_MountCancelinfo $$$$$");
            }
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPackDelivered_StockInfo(@Payload PackDelivered packDelivered){
        // stock으로 부터 Pack Info를 전달받는다.
        if(!packDelivered.validate()) return;

        System.out.println("$$$$$ listener StockInfo : " + packDelivered.toJson() + " $$$$$");

        MountPart mountPart = mountPartRepository.findByOrderId(packDelivered.getOrderId());

        if (mountPart != null){
            mountPart.setStockPackStatus("PACK_DELIVERED");
            mountPartRepository.save(mountPart);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPackDeliveryCanceled_StockInfo(@Payload PackDeliveryCanceled packDeliveryCanceled){
        // stock으로 부터 Pack Cancel Info를 전달받는다.
        if(!packDeliveryCanceled.validate()) return;

        System.out.println("$$$$$ listener StockInfo : " + packDeliveryCanceled.toJson() + " $$$$$");

        MountPart mountPart = mountPartRepository.findByOrderId(packDeliveryCanceled.getOrderId());
        if (mountPart != null){
            mountPart.setStockPackStatus("PACK_DELIVERY_CANCELED");
            mountPartRepository.save(mountPart);
        }
    }	


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}
}