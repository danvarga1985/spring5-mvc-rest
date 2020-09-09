package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {
    private static final String FIRSTNAME = "Ernst";
    private static final String LASTNAME = "Junger";
    private static final long ID = 1L;
    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testListAllCustomers() throws Exception {
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(ID);
        customer1.setFirstName(FIRSTNAME);
        customer1.setLastName(LASTNAME);

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstName("John");
        customer2.setLastName("Keats");

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRSTNAME);
        customerDTO.setLastName(LASTNAME);
        customerDTO.setId(ID);

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get(CustomerController.BASE_URL + "/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));

    }

    @Test
    public void createNewCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName(FIRSTNAME);
        customer.setLastName(LASTNAME);
        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

        String response = mockMvc.perform(post(CustomerController.BASE_URL + "/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Yukio");
        customer.setLastName("Mishima");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.updateCustomerById(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(CustomerController.BASE_URL + "/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Yukio")))
                .andExpect(jsonPath("$.lastName", equalTo("Mishima")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Yukio");
        customer.setLastName("Mishima");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Yukio")))
                .andExpect(jsonPath("$.lastName", equalTo("Mishima")))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/1")));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }
}