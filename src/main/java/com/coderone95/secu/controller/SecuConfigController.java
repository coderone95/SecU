package com.coderone95.secu.controller;

import com.coderone95.secu.entity.SecBankDetails;
import com.coderone95.secu.entity.User;
import com.coderone95.secu.exceptions.GenericException;
import com.coderone95.secu.model.SuccessResponse;
import com.coderone95.secu.service.SecUConfigService;
import com.coderone95.secu.service.UserService;
import com.coderone95.secu.utility.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class SecuConfigController {

    @Autowired
    private SecUConfigService secuConfigService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/save_bank_details")
    public ResponseEntity<?> addBankingDetails(@RequestParam("email_id") String emailId,
              @RequestBody SecBankDetails secBankDetails){
        try{
            if(StringUtils.isNullOrBlank(emailId)){
                throw new GenericException("Please enter email address");
            }else{
                boolean isUserExistsByEmail = userService.isUserExistsByEmail(emailId);
                if(!isUserExistsByEmail){
                    throw new GenericException("Invalid User");
                }
                if(StringUtils.isNullOrBlank(secBankDetails.getSecName())){
                    throw new GenericException("Bank Sec name cannot be blanked");
                }
                User user = userService.getUserByEmailId(emailId);
                SecBankDetails data = secuConfigService.saveBankDetails(user,secBankDetails);
            }
        }catch(Exception e){
            throw new GenericException(e.getMessage());
        }
        SuccessResponse sr = new SuccessResponse("Bank Security information added successfully", "SUCCESS");
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }

    @GetMapping(value = "/get_bk_details/{id}")
    public ResponseEntity<?> getBKDetailsById(Long id){
        SecBankDetails data = new SecBankDetails();
        try{
            if(!secuConfigService.isBKExistsById(id)){
                throw new GenericException("Invalid data id");
            }
            data = secuConfigService.getBKDetailsById(id);
        }catch(Exception e){
            throw new GenericException(e.getMessage());
        }
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @GetMapping(value = "/get_bk_details")
    public ResponseEntity<?> getBKDetailsById(@RequestParam("sec_name") String secName){
        SecBankDetails data = new SecBankDetails();
        try{
            if(!secuConfigService.isBKExistsBySecName(secName)){
                throw new GenericException("Invalid data sec name");
            }
            data = secuConfigService.getBKDetailsBySecName(secName);
        }catch(Exception e){
            throw new GenericException(e.getMessage());
        }
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

}
