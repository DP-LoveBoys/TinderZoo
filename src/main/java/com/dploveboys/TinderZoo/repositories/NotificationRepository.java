package com.dploveboys.TinderZoo.repositories;

import com.dploveboys.TinderZoo.model.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification,Long> {
    List<Notification> findByUserId(Long userId);
}
