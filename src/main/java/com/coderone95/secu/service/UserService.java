package com.coderone95.secu.service;

import com.coderone95.secu.entity.User;
import com.coderone95.secu.utility.CryptUtils;
import com.coderone95.secu.utility.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.coderone95.secu.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service("userService")
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	private Environment env;

	public void saveUser(User u) {
		String encryptedPwd = CryptUtils.encrypt(u.getPassword(),CryptUtils.cipher_Key);
		String encryptedProfilePic = CryptUtils.encrypt(u.getProfilePicData(),CryptUtils.cipher_Key);
		u.setPassword(encryptedPwd);
		u.setProfilePicData(encryptedProfilePic);
		userRepository.save(u);
	}

	public boolean isUserExistsByEmail(String emailId) {
		User u = userRepository.findByEmailId(emailId);
		if(u != null){
			return true;
		}
		return false;
	}

	public boolean isUserExistsByEmailAndPassword(String emailId, String password) {
		String encryptedPwd = CryptUtils.encrypt(password, CryptUtils.cipher_Key);
		User user = userRepository.findByEmailIdAndPassword(emailId,encryptedPwd);
		if(user !=null){
			return true;
		}
		return false;
	}

	public User getUserByEmailId(String emailId) {
		User user = userRepository.findByEmailId(emailId);
		return user;
	}

	public boolean forgotPassword(String emailId) throws Exception {
		try{
			User user = userRepository.findByEmailId(emailId);
			String decryptPwd = CryptUtils.decrypt(user.getPassword(),CryptUtils.cipher_Key);
			System.out.println("Decrypted Pwd::: "+decryptPwd);
			//send email to user
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public boolean resetPassword(String emailId, String newPwd) {
		try{
			User user = userRepository.findByEmailId(emailId);
			String encryptedPwd = CryptUtils.encrypt(newPwd,CryptUtils.cipher_Key);
			user.setPassword(encryptedPwd);
			System.out.println("New Pwd::: "+newPwd);
			userRepository.save(user);
			//send rest pwd email to user
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public void updateUser(User user, String emailId) {
		User u = userRepository.findByEmailId(emailId);
		if(!StringUtils.isNullOrBlank(user.getPassword())){
			String encryptedPwd = CryptUtils.encrypt(user.getPassword(),CryptUtils.cipher_Key);
			u.setPassword(encryptedPwd);
		}
		if(!StringUtils.isNullOrBlank(user.getName())){
			u.setName(user.getName());
		}
		if(!StringUtils.isNullOrBlank(user.getPhone())){
			u.setPhone(user.getPhone());
		}
		if(!StringUtils.isNullOrBlank(user.getProfilePicData())){
			u.setProfilePicData(user.getProfilePicData());
		}
		userRepository.save(u);
	}

	public void deleteUser(String emailId) {
		Long userId = getUserIdByEmailId(emailId);
		userRepository.deleteById(userId);
	}

	public Long getUserIdByEmailId(String emailId){
		User u = userRepository.findByEmailId(emailId);
		return u.getUserId();
	}

	public void uploadProfilePic(MultipartFile imageFile, String emailId) throws IOException {
		if (imageFile.getSize() > 0L) {
			User u = userRepository.findByEmailId(emailId);
			Date now = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String dateString= formatter.format(now);
			String path = env.getProperty("file.upload-dir")+"/"+dateString;
			File dir = new File(path);
			if(!dir.exists())
				dir.mkdir();
			String originalFileName = imageFile.getOriginalFilename();
//			String extension = FilenameUtils.getExtension(imageFile.getOriginalFilename());
			String generatedFileName = originalFileName;
			File newFile = new File(dir.getPath() + File.separator + generatedFileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			System.out.println(newFile.getPath());
			OutputStream outputStream = new FileOutputStream(newFile);
			outputStream.write(imageFile.getBytes());
			outputStream.flush();
			outputStream.close();
//			String baseValue = Base64.encodeBase64String(imageFile.getBytes());
			String encryptedValue = CryptUtils.encrypt(newFile.getPath(),CryptUtils.cipher_Key);
			u.setProfilePicData(encryptedValue);
			userRepository.save(u);
		}
	}
}
