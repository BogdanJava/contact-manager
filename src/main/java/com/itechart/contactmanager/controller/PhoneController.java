package com.itechart.contactmanager.controller;

import com.itechart.contactmanager.model.Phone;
import com.itechart.contactmanager.payload.ApiResponse;
import com.itechart.contactmanager.payload.PhoneRequest;
import com.itechart.contactmanager.security.CurrentUser;
import com.itechart.contactmanager.security.CustomUserDetails;
import com.itechart.contactmanager.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/phone")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @PostMapping
    public ResponseEntity addPhone(@CurrentUser CustomUserDetails userDetails,
                                   @Valid @RequestBody PhoneRequest phoneRequest) {
        Phone phone = phoneService.save(phoneRequest, userDetails);
        return ResponseEntity.ok(new ApiResponse(true, "Phone " + phone.getNumber() +
                " has been successfully created."));
    }

    @DeleteMapping("/{phoneId}")
    public ResponseEntity deletePhone(@CurrentUser CustomUserDetails userDetails,
                                      @PathVariable long phoneId) {
        phoneService.deletePhone(phoneId, userDetails);
        return ResponseEntity.ok(new ApiResponse(true, "Phone #" + phoneId + " successfully deleted."));
    }

    @GetMapping("/by-employee/{employeeId}")
    public ResponseEntity getPhonesByEmployeeId(@CurrentUser CustomUserDetails userDetails,
                                    @PathVariable long employeeId) {
        return ResponseEntity.ok(phoneService.getPhones(employeeId, userDetails));
    }

    @GetMapping("/{phoneId}")
    public ResponseEntity getPhoneById(@CurrentUser CustomUserDetails userDetails,
                                       @PathVariable long phoneId) {
        return ResponseEntity.ok(phoneService.getPhone(phoneId, userDetails));
    }
}
