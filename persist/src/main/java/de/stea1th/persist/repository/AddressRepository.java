package de.stea1th.persist.repository;

import de.stea1th.persist.entity.Address;
import lombok.var;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AddressRepository extends JpaRepository<Address, Integer> {

    default Address createEmptyAddress() {
        var address = findAddressByStreetAndZipAndCityAndCountry(null, null, null, null);
        if(address == null) address = this.save(new Address());
        return address;
    }

    Address findAddressByStreetAndZipAndCityAndCountry(String street, String zip, String city, String country);



}
