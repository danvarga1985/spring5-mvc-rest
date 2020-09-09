package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {
    public static final String FIRSTNAME = "Pac";
    public static final String LASTNAME = "Man";
    public static final Long ID = 1L;
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void testCustomerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);
        customer.setId(ID);

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals("Pac", customerDTO.getFirstName());
        assertEquals("Man", customerDTO.getLastName());
    }

    @Test
    public void testCustomerDTOToCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Pack");
        customerDTO.setLastName("Rat");


    }
}