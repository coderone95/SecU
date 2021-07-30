package com.coderone95.secu.service;

import com.coderone95.secu.beans.SignupBean;
import com.coderone95.secu.entity.User;
import com.coderone95.secu.exceptions.GenericException;
import com.coderone95.secu.utility.Constants;
import com.coderone95.secu.utility.CryptUtils;
import com.coderone95.secu.utility.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.coderone95.secu.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Service("userService")
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	private Environment env;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Saves the user
	 * @param signupBean
	 * @return JSONObject
	 */
	public JSONObject saveUser(SignupBean signupBean) {
		JSONObject jsonObject = new JSONObject();
		boolean isValid = validateEmailAddress(signupBean.getEmailId());
		if(!isValid){
			throw  new GenericException("Invalid Email Address");
		}
		String encryptedPwd = CryptUtils.encrypt(signupBean.getPassword(),CryptUtils.cipher_Key);
		boolean isValidPwd = validatePassword(signupBean.getPassword(),signupBean.getConfirmPassword());
		if(!isValidPwd){
			throw  new GenericException("Password not matched");
		}
		User user = new User();
		user.setPassword(encryptedPwd);
		user.setName(signupBean.getName());
		user.setPhone(signupBean.getPhone());
		user.setEmail_id(signupBean.getEmailId());
		user.setSecret_key(CryptUtils.encrypt(CryptUtils.cipher_Key,signupBean.getSecretKey()));
		User saved = userRepository.save(user);
		if(saved != null){
			JSONObject data = new JSONObject();
			jsonObject.put(Constants.STATUS,Constants.SUCCESS);
			jsonObject.put(Constants.MESSAGE,"User added successfully");
			data.put("user_id",saved.getUser_id());
			data.put("email_id",saved.getEmail_id());
			data.put("name",saved.getName());
			jsonObject.put(Constants.DATA,data);
		}else{
			throw new GenericException("Erorr while saving user");
		}
		return jsonObject;
	}

	private boolean validatePassword(String password, String confirmPassword) {
		if(StringUtils.isNullOrBlank(password) || StringUtils.isNullOrBlank(confirmPassword)){
			return false;
		}
		return password.equals(confirmPassword);
	}

	private boolean validateEmailAddress(String email_id) {
		boolean result = true;
		try{
			String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
					"[a-zA-Z0-9_+&*-]+)*@" +
					"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
					"A-Z]{2,7}$";

			Pattern pat = Pattern.compile(emailRegex);
			if (email_id == null || email_id == "")
				return false;
			if(!pat.matcher(email_id).matches()){
				return false;
			}
			InternetAddress emailAddr = new InternetAddress(email_id);
			emailAddr.validate();
		}catch (Exception e){
			result = false;
		}
		return result;
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
		if(!StringUtils.isNullOrBlank(user.getProfile_pic_data())){
			u.setProfile_pic_data(user.getProfile_pic_data());
		}
		userRepository.save(u);
	}

	public void deleteUser(String emailId) {
		Long userId = getUserIdByEmailId(emailId);
		userRepository.deleteById(userId);
	}

	public Long getUserIdByEmailId(String emailId){
		User u = userRepository.findByEmailId(emailId);
		return u.getUser_id();
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
			u.setProfile_pic_data(encryptedValue);
			userRepository.save(u);
		}
	}
}
