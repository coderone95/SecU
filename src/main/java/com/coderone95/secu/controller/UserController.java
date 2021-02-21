package com.coderone95.secu.controller;

import com.coderone95.secu.entity.User;
import com.coderone95.secu.exceptions.GenericException;
import com.coderone95.secu.model.CustomResponse;
import com.coderone95.secu.model.SuccessResponse;
import com.coderone95.secu.service.UserService;
import com.coderone95.secu.utility.CryptUtils;
import com.coderone95.secu.utility.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value="/register")
    public ResponseEntity<?> addUser(@Valid  @RequestBody User user){
        JSONObject json = new JSONObject();
        try{
            if(StringUtils.isNullOrBlank(user.getPassword())){
                throw new GenericException("Password is mandatory");
            }else if(StringUtils.isNullOrBlank(user.getEmail_id())){
                throw new GenericException("Email is mandatory");
            }else{
                boolean isUserExistsByEmail = userService.isUserExistsByEmail(user.getEmail_id());
                if(isUserExistsByEmail){
                    throw new GenericException("User already exists with "+user.getEmail_id());
                }else{
                    json = userService.saveUser(user);
                }
            }
        }catch(Exception e){
            throw new GenericException(e.getMessage());
        }
        CustomResponse res = new CustomResponse(json);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(value="/validate_user")
    public ResponseEntity<?> validateUser(@RequestParam("email_id") String emailId,
                                          @RequestParam("password") String password, HttpSession session){
        try{
            if(StringUtils.isNullOrBlank(emailId)){
                throw new GenericException("Please enter email address");
            }else if(StringUtils.isNullOrBlank(password)){
                throw new GenericException("Please enter password");
            }else{
                boolean isUserExistsByEmail = userService.isUserExistsByEmailAndPassword(emailId,password);
                if(!isUserExistsByEmail){
                    throw new GenericException("Invalid User");
                }else{
                    User user = userService.getUserByEmailId(emailId);
                    session.setAttribute("user_id",user.getUser_id());
                    session.setAttribute("user_email",user.getEmail_id());
                }
            }
        }catch(Exception e){
            throw new GenericException(e.getMessage());
        }
        SuccessResponse sr = new SuccessResponse("Valid User", "SUCCESS");
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }

    @PostMapping(value = "/forgot_pwd")
    public ResponseEntity<?> forgotPwd(@RequestParam("email_id") String emailId){
        try{
            if(StringUtils.isNullOrBlank(emailId)){
                throw new GenericException("Please enter email address");
            }else{
                boolean isUserExistsByEmail = userService.isUserExistsByEmail(emailId);
                if(!isUserExistsByEmail){
                    throw new GenericException("Invalid User");
                }
                boolean sentPwd = userService.forgotPassword(emailId);
                if(!sentPwd){
                    throw  new GenericException("Error while sending an email. Please try after sometime");
                }
            }
        }catch(Exception e){
            throw new GenericException(e.getMessage());
        }
        SuccessResponse sr = new SuccessResponse("Password has been sent to registered email "+emailId, "SUCCESS");
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }

    @PostMapping(value = "/reset_pwd")
    public ResponseEntity<?> resetPwd(@RequestParam("email_id") String emailId,
            @RequestParam("old_pwd") String oldPwd, @RequestParam("new_pwd") String newPwd){
        try{
            if(StringUtils.isNullOrBlank(emailId)){
                throw new GenericException("Please enter email address");
            }else{
                boolean isUserExistsByEmail = userService.isUserExistsByEmail(emailId);
                if(!isUserExistsByEmail){
                    throw new GenericException("Invalid User");
                }else{
                    User user = userService.getUserByEmailId(emailId);
                    if(!CryptUtils.decrypt(user.getPassword(),CryptUtils.cipher_Key).equals(oldPwd)){
                        throw new GenericException("Authentication Failed. Old password is not valid");
                    }
                }

                boolean resetPwd = userService.resetPassword(emailId,newPwd);
                if(!resetPwd){
                    throw  new GenericException("Error while resting password. Please try after sometime");
                }
            }
        }catch(Exception e){
            throw new GenericException(e.getMessage());
        }
        SuccessResponse sr = new SuccessResponse("Password has been reset successfully", "SUCCESS");
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateUser(@RequestBody User user,
                  @RequestParam("email_id") String emailId){
        try{
            if(StringUtils.isNullOrBlank(emailId)){
                throw new GenericException("Please provide the valid login");
            }else{
                boolean isUserExistsByEmail = userService.isUserExistsByEmail(emailId);
                if(!isUserExistsByEmail){
                    throw new GenericException("Invalid User");
                }else{
                    userService.updateUser(user,emailId);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new GenericException(e.getMessage());
        }
        SuccessResponse sr = new SuccessResponse("User updated successfully", "SUCCESS");
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("email_id") String emailId){
        try{
            if(StringUtils.isNullOrBlank(emailId)){
                throw new GenericException("Please provide the valid user");
            }else{
                boolean isUserExistsByEmail = userService.isUserExistsByEmail(emailId);
                if(!isUserExistsByEmail){
                    throw new GenericException("Invalid User");
                }else{
                    userService.deleteUser(emailId);
                }
            }
        }catch(Exception e){
            throw new GenericException(e.getMessage());
        }
        SuccessResponse sr = new SuccessResponse("User deleted successfully", "SUCCESS");
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }

    @PostMapping(value = "/upload_profile_pic")
    public ResponseEntity<?> uploadProfilePic(@RequestParam("email_id") String emailId,
            @RequestParam("file") MultipartFile image){
        try{
            if(StringUtils.isNullOrBlank(emailId)){
                throw new GenericException("Please provide the valid user");
            }else{
                boolean isUserExistsByEmail = userService.isUserExistsByEmail(emailId);
                if(!isUserExistsByEmail){
                    throw new GenericException("Invalid User");
                }else{
                    userService.uploadProfilePic(image,emailId);
                }
            }
        }catch(Exception e){
            throw new GenericException(e.getMessage());
        }
        SuccessResponse sr = new SuccessResponse("Profile pic uploaded successfully", "SUCCESS");
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }





}
