package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.services.CustomerService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Customer Ctrl"})
@SwaggerDefinition(tags = {
        @Tag(name = "Customer Ctrl", description = "This is my Customer Controller")
})
//@Info(title = "My controller", description = "not default description")
@Controller
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    public static final String BASE_URL = "/api/v1/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "This will get a list of customers.", notes = "These are some notes about the API")
    @GetMapping
    public ResponseEntity<CustomerListDTO> listAllCustomers() {
        return new ResponseEntity<>(
                new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return new ResponseEntity<>(
                customerService.getCustomerById(id), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO) {
       return new ResponseEntity<>(customerService.createNewCustomer(customerDTO),
            HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<CustomerDTO> updateCustomerById(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.updateCustomerById(id, customerDTO),
                HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<CustomerDTO> patchCustomerById(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.patchCustomer(id, customerDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
