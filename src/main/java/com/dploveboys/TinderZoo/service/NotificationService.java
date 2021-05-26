package com.dploveboys.TinderZoo.service;

import com.dploveboys.TinderZoo.model.Notification;
import com.dploveboys.TinderZoo.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getNotifications(Long userId){
        return notificationRepository.findByUserId(userId);
    }

    public void deleteNotification(Long notificationId){
        notificationRepository.deleteById(notificationId);
    }

    public Notification createNotification(Long userId,Long matchId,String type){
        Notification notification=new Notification();
        notification.setUserId(userId);
        notification.setPretendentId(matchId);
        notification.setType(type);
        return notification;
    }

    public void addNotification(Notification notification){
        notificationRepository.save(notification);
    }

    public int getNotificationCount(Long userId){
        return getNotifications(userId).size();
    }
}
