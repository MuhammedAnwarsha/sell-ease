package com.se.service.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.se.entity.Order;
import com.se.payload.dto.OrderNotificationDto;

@Service
public class NotificationService {
	
	private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNewOrderNotification(Order order) {
        OrderNotificationDto dto = new OrderNotificationDto();
        dto.setOrderId(order.getId());
        dto.setProductName(order.getProduct().getProductName());
        dto.setBuyerName(order.getBuyerName());
        dto.setQuantity(order.getQuantity());
        dto.setStatus(order.getStatus().name());

        messagingTemplate.convertAndSend("/topic/orders/" + order.getProduct().getSeller().getId(), dto);
    }
}
