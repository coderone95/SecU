package com.coderone95.secu.service;

import com.coderone95.secu.entity.SecBankDetails;
import com.coderone95.secu.entity.User;
import com.coderone95.secu.repository.SecBankDetailsRepository;
import com.coderone95.secu.utility.CryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("secuConfigService")
public class SecUConfigService {

    @Autowired
    private SecBankDetailsRepository secBankDetailsRepository;

    public SecBankDetails saveBankDetails(User user, SecBankDetails secBankDetails) {
        secBankDetails.setUser(user);
        String encryptedUrl = CryptUtils.encrypt(secBankDetails.getBankingURL(),CryptUtils.cipher_Key);
        secBankDetails.setBankingURL(encryptedUrl);
        String encryptedAH = CryptUtils.encrypt(secBankDetails.getAccountHolder(),CryptUtils.cipher_Key);
        secBankDetails.setAccountHolder(encryptedAH);
        String encryptedPWD = CryptUtils.encrypt(secBankDetails.getPassword(),CryptUtils.cipher_Key);
        secBankDetails.setPassword(encryptedPWD);
        String encryptedBranch = CryptUtils.encrypt(secBankDetails.getBranch(),CryptUtils.cipher_Key);
        secBankDetails.setBranch(encryptedBranch);
        String encryptedICMR = CryptUtils.encrypt(secBankDetails.getIcmrCode(),CryptUtils.cipher_Key);
        secBankDetails.setIcmrCode(encryptedICMR);
        String encryptedIFSC = CryptUtils.encrypt(secBankDetails.getIfscCode(),CryptUtils.cipher_Key);
        secBankDetails.setIfscCode(encryptedIFSC);
        String encryptedOtherData = CryptUtils.encrypt(secBankDetails.getOtherData(),CryptUtils.cipher_Key);
        secBankDetails.setOtherData(encryptedOtherData);
        String encryptedUserName = CryptUtils.encrypt(secBankDetails.getUsername(),CryptUtils.cipher_Key);
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
}
