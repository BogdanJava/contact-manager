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
        Employee saved = employeeService.save(employeeRequest.create(userDao.findByUsername(userDetails.getUsername())));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{employeeId}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Employee created successfully"));
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity deleteEmployee(@CurrentUser CustomUserDetails userDetails,
                                         @PathVariable long employeeId) {
        employeeService.delete(employeeId, userDetails);
        return ResponseEntity.ok(new ApiResponse(true, "Employee #" + employeeId + " " +
                "successfully deleted"));
    }

    @PutMapping
    public ResponseEntity updateEmployee(@CurrentUser CustomUserDetails userDetails,
                                         @Valid @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(
                employeeService.update(employeeRequest.create(employeeDao.findOne(employeeRequest.getId()).getUser()),
                        userDetails));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity getById(@CurrentUser CustomUserDetails userDetails,
                                  @PathVariable long employeeId) {
        return ResponseEntity.ok(employeeService.getById(employeeId, userDetails));
    }
}
