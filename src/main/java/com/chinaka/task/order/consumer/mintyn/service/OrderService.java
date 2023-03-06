package com.chinaka.task.order.consumer.mintyn.service;

import com.chinaka.task.order.consumer.mintyn.dto.GenericResponse;
import com.chinaka.task.order.consumer.mintyn.dto.OrderDto;

import java.util.Date;

public interface OrderService {
    GenericResponse getOrder(String id);

    GenericResponse listOrder(int pageNo, int pageSize, String startDate, String endDate);
}
