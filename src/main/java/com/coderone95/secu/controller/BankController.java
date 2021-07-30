package com.coderone95.secu.controller;

import com.coderone95.secu.beans.BankBean;
import com.coderone95.secu.entity.SecBankDetails;
import com.coderone95.secu.entity.User;
import com.coderone95.secu.exceptions.GenericException;
import com.coderone95.secu.model.SuccessResponse;
import com.coderone95.secu.service.SecUConfigService;
import com.coderone95.secu.service.UserService;
import com.coderone95.secu.utility.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SecUConfigService secuConfigService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/saveBankDetails")
    public ResponseEntity<?> addBankingDetails(@RequestParam("email_id") String emailId,
              @Valid @RequestBody BankBean bank){
        LOGGER.info("START >> CLASS >> SecuConfigController >> METHOD >> addBankingDetails");

        if(StringUtils.isNullOrBlank(emailId)){
            LOGGER.error("ERROR >> CLASS >> SecuConfigController >> METHOD >> addBankingDetails");
            throw new GenericException("Please enter email address");
        }else{
            boolean isUserExistsByEmail = userService.isUserExistsByEmail(emailId);
            if(!isUserExistsByEmail){
                LOGGER.error("ERROR >> CLASS >> SecuConfigController >> METHOD >> addBankingDetails >> Invalid User");
                throw new GenericException("Invalid User");
            }
            if(StringUtils.isNullOrBlank(bank.getSecName())){
                LOGGER.error("ERROR >> CLASS >> SecuConfigController >> METHOD >> addBankingDetails >> Bank Sec name cannot be blanked");
                throw new GenericException("Bank Sec name cannot be blanked");
            }
            User user = userService.getUserByEmailId(emailId);
            SecBankDetails data = secuConfigService.saveBankDetails(user,bank);
        }
        LOGGER.info("START >> CLASS >> SecuConfigController >> METHOD >> addBankingDetails");
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

    @GetMapping(value = "/getBankDetails")
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

    @GetMapping(value = "/getAllBankDetails")
    public ResponseEntity<?> getAllBankDetails(){
        LOGGER.info("START >> CLASS >> BankController >> METHOD >> getAllBankDetails ");
        List<SecBankDetails> list = secuConfigService.getAllBankDetailsList();
        LOGGER.info("END >> CLASS >> BankController >> METHOD >> getAllBankDetails ");
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

}
