package de.stealth.personservice.repository;

import de.stealth.personservice.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void createEmptyAddress_CheckAddressIdNotNull() {

        Address current = addressRepository.createEmptyAddress();

        assertNotNull(current.getId());
        assertNull(current.getStreet());
    }
}
