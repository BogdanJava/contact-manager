package com.itechart.contactmanager.controller;

import com.itechart.contactmanager.dao.EmployeeDao;
import com.itechart.contactmanager.dao.UserDao;
import com.itechart.contactmanager.model.Employee;
import com.itechart.contactmanager.model.Phone;
import com.itechart.contactmanager.payload.ApiResponse;
import com.itechart.contactmanager.payload.EmployeeRequest;
import com.itechart.contactmanager.payload.PhoneRequest;
import com.itechart.contactmanager.security.CurrentUser;
import com.itechart.contactmanager.security.CustomUserDetails;
import com.itechart.contactmanager.service.EmployeeService;
import com.itechart.contactmanager.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(@CurrentUser CustomUserDetails userDetails) {
        return ResponseEntity.ok(employeeDao.getByUserId(userDetails.getId()));
    }

    @PostMapping
    public ResponseEntity addEmployee(@CurrentUser CustomUserDetails userDetails,
                                      @Valid @RequestBody EmployeeRequest employeeRequest) {
        Employee saved = employeeDao.save(employeeRequest.create(userDao.findByUsername(userDetails.getUsername())));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{employeeId}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Employee created successfully"));
    }

    @PostMapping("/phone")
    public ResponseEntity addPhone(@CurrentUser CustomUserDetails userDetails,
                                   @Valid @RequestBody PhoneRequest phoneRequest) {
        Phone phone = phoneService.save(phoneRequest, userDetails);
        return ResponseEntity.ok(new ApiResponse(true, "Phone " + phone.getNumber() +
                " has been successfully created."));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity getById(@CurrentUser CustomUserDetails userDetails,
                                  @PathVariable long employeeId) {
        return ResponseEntity.ok(employeeService.getById(employeeId, userDetails));
    }

    @GetMapping("/{employeeId}/phones")
    public ResponseEntity getPhones(@CurrentUser CustomUserDetails userDetails,
                                    @PathVariable long employeeId) {
        return ResponseEntity.ok(phoneService.getPhones(employeeId, userDetails));
    }

    @GetMapping("/phone/{phoneId}")
    public ResponseEntity getPhoneById(@CurrentUser CustomUserDetails userDetails,
                                       @PathVariable long phoneId) {
        return ResponseEntity.ok(phoneService.getPhone(phoneId, userDetails));
    }
}
