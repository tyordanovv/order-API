package bg.tyordanovv.delivery;

import bg.tyordanovv.delivery.persistence.DeliveryEntity;
import bg.tyordanovv.delivery.persistence.DeliveryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersistenceTests extends PostgresTestBase{
    @Autowired
    private DeliveryRepository repository;
    private DeliveryEntity savedEntity;

    @BeforeEach
    void setupDb() {
        repository.deleteAll();

        DeliveryEntity entity =
                new DeliveryEntity("Burgas", 12345L, 111L, "Cake", 2);
        savedEntity = repository.save(entity);

        assertEqualsDelivery(entity, savedEntity);
    }

    @Test
    void create() {

        DeliveryEntity newEntity =
                new DeliveryEntity("Sofia", 11111L, 222L, "Yogurt", 4);
        repository.save(newEntity);

        DeliveryEntity foundEntity = repository.findById(newEntity.getId()).get();
        assertEqualsDelivery(newEntity, foundEntity);

        assertEquals(2, repository.count());
    }

    @Test
    void delete() {
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    void getByProductId() {
        List<DeliveryEntity> entityList = repository.findAllByOrderId(savedEntity.getOrderId()).get();

        assertThat(entityList, hasSize(1));
        assertEqualsDelivery(savedEntity, entityList.get(0));
    }

//    @Test //TODO add error msg
//    void duplicateError() {
//        assertThrows(DataIntegrityViolationException.class, () -> {
//            DeliveryEntity entity =
//                    new DeliveryEntity("Burgas", 12345L, 111L, "Cake", 2);
//            entity.setId(savedEntity.getId());
//            repository.save(entity);
//        });
//    }

    private void assertEqualsDelivery(DeliveryEntity expectedEntity, DeliveryEntity actualEntity) {
        assertEquals(expectedEntity.getId(),        actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),   actualEntity.getVersion());
        assertEquals(expectedEntity.getProductId(), actualEntity.getProductId());
        assertEquals(expectedEntity.getAddress(),  actualEntity.getAddress());
        assertEquals(expectedEntity.getOrderId(),    actualEntity.getOrderId());
        assertEquals(expectedEntity.getProductId(),   actualEntity.getProductId());
        assertEquals(expectedEntity.getProductName(),   actualEntity.getProductName());
        assertEquals(expectedEntity.getOrderedAmount(),   actualEntity.getOrderedAmount());
    }
}