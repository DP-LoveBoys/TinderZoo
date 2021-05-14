package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.ProfilePicture;
import com.dploveboys.TinderZoo.repositories.ProfilePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class ProfilePictureService {

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    public static final String FIND_PROFILE_PICTURE = "SELECT image FROM profile_photos WHERE user_id=?1";


    public void saveProfilePicture(MultipartFile file,Long userId){
        ProfilePicture profilePicture=new ProfilePicture();

        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")){
            System.out.println("Not a valid file!");
        }

        try {
            profilePicture.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        profilePicture.setUser_id(userId);

        profilePictureRepository.save(profilePicture);
    }


    public ProfilePicture getProfilePicture(Long userId){
        return profilePictureRepository.findByUserId(userId);
    }
}
