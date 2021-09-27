package chargingstation;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="mountParts", path="mountParts")
public interface MountPartRepository extends JpaRepository<MountPart, Long>{

    MountPart findByOrderId(Long orderId);
    MountPart findByOrderPackType(String orderPackType);
    MountPart findByOrderPackTypeAndOrderStatus(String orderPackType, String orderStatus);
}
