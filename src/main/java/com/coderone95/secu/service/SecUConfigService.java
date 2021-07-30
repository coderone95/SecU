package com.coderone95.secu.service;

import com.coderone95.secu.beans.BankBean;
import com.coderone95.secu.controller.UserController;
import com.coderone95.secu.entity.SecBankDetails;
import com.coderone95.secu.entity.User;
import com.coderone95.secu.exceptions.GenericException;
import com.coderone95.secu.repository.SecBankDetailsRepository;
import com.coderone95.secu.repository.UserRepository;
import com.coderone95.secu.utility.CryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service("secuConfigService")
public class SecUConfigService {

    private final Logger LOGGER = LoggerFactory.getLogger(SecUConfigService.class);

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecBankDetailsRepository secBankDetailsRepository;

    public SecBankDetails saveBankDetails(User user, BankBean bank) {
        String cipher_key = CryptUtils.cipher_Key;
        if(user.getSecret_key() != null){
            cipher_key = user.getSecret_key();
        }
        SecBankDetails secBankDetails = new SecBankDetails();
        secBankDetails.setSecName(bank.getSecName());
        secBankDetails.setAccountHolder(bank.getAccountHolder());
        secBankDetails.setAccountNumber(bank.getAccountNumber());
        secBankDetails.setUser(user);
        String encryptedUrl = CryptUtils.encrypt(bank.getBankingURL(),cipher_key);
        secBankDetails.setBankingURL(encryptedUrl);
        String encryptedAH = CryptUtils.encrypt(bank.getAccountHolder(),cipher_key);
        secBankDetails.setAccountHolder(encryptedAH);
        String encryptedPWD = CryptUtils.encrypt(bank.getPassword(),cipher_key);
        secBankDetails.setPassword(encryptedPWD);
        String encryptedBranch = CryptUtils.encrypt(bank.getBranch(),cipher_key);
        secBankDetails.setBranch(encryptedBranch);
        String encryptedICMR = CryptUtils.encrypt(bank.getIcmrCode(),cipher_key);
        secBankDetails.setIcmrCode(encryptedICMR);
        String encryptedIFSC = CryptUtils.encrypt(bank.getIfscCode(),cipher_key);
        secBankDetails.setIfscCode(encryptedIFSC);
        String encryptedOtherData = CryptUtils.encrypt(bank.getOtherData(),cipher_key);
        secBankDetails.setOtherData(encryptedOtherData);
        String encryptedUserName = CryptUtils.encrypt(bank.getUsername(),cipher_key);
        secBankDetails.setUsername(encryptedUserName);
        return secBankDetailsRepository.save(secBankDetails);
    }

    public boolean isBKExistsById(Long id) {
        return secBankDetailsRepository.existsById(id);
    }

    public SecBankDetails getBKDetailsById(Long id) {
        return secBankDetailsRepository.getOne(id);
    }

    public boolean isBKExistsBySecName(String secName) {
        SecBankDetails data = secBankDetailsRepository.getBKDetailsBySecName(secName);
        if(data != null){
            return true;
        }
        return false;
    }

    public SecBankDetails getBKDetailsBySecName(String secName) {
        return secBankDetailsRepository.getBKDetailsBySecName(secName);
    }

    public List<SecBankDetails> getAllBankDetailsList() {
        LOGGER.info("START >> CLASS >> SecUConfigService >> METHOD >> getAllBankDetailsList");
        Long userId = (Long) session.getAttribute("user_id");
        if(userId == null){
            LOGGER.error("ERROR >> CLASS >> SecUConfigService >> METHOD >> getAllBankDetailsList >> Error while getting the bank account details");
            throw  new GenericException("ERROR WHILE GETTING THE LIST");
        }
        User user = userRepository.getOne(userId);
        String secret_key = user.getSecret_key();
        if(secret_key == null || secret_key == ""){
            secret_key = CryptUtils.cipher_Key;
        }
        List<SecBankDetails> list = secBankDetailsRepository.findAll();
        String finalSecret_key = secret_key;
        list = list.stream().map(o->{
            try {
                o.setAccountHolder(CryptUtils.decrypt(finalSecret_key,o.getAccountHolder()));
                o.setBankingURL(CryptUtils.decrypt(finalSecret_key,o.getBankingURL()));
                o.setBranch(CryptUtils.decrypt(finalSecret_key,o.getBranch()));
                o.setIcmrCode(CryptUtils.decrypt(finalSecret_key,o.getIcmrCode()));
                o.setPassword(CryptUtils.decrypt(finalSecret_key,o.getPassword()));
                o.setAccountNumber(CryptUtils.decrypt(finalSecret_key,o.getAccountNumber()));
                o.setIfscCode(CryptUtils.decrypt(finalSecret_key,o.getIfscCode()));
                o.setUsername(CryptUtils.decrypt(finalSecret_key,o.getUsername()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return o;
        }).collect(Collectors.toList());
        LOGGER.info("END >> CLASS >> SecUConfigService >> METHOD >> getAllBankDetailsList");
        return list;
    }
}
