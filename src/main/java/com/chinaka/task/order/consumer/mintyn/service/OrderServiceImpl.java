package com.chinaka.task.order.consumer.mintyn.service;

import com.chinaka.task.order.consumer.mintyn.dto.GenericResponse;
import com.chinaka.task.order.consumer.mintyn.model.Order;
import com.chinaka.task.order.consumer.mintyn.dto.OrderDto;
import com.chinaka.task.order.consumer.mintyn.model.Product;
import com.chinaka.task.order.consumer.mintyn.repository.OrderRepository;
import com.chinaka.task.order.consumer.mintyn.repository.ProductRepository;
import com.chinaka.task.order.consumer.mintyn.util.ResponseHelper;
import com.chinaka.task.order.consumer.mintyn.validation.OrderExistException;
import com.chinaka.task.order.consumer.mintyn.validation.ProductExistException;
import com.chinaka.task.order.consumer.mintyn.util.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static com.chinaka.task.order.consumer.mintyn.util.Constants.*;
import static com.chinaka.task.order.consumer.mintyn.util.Constants.SUCCESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ResponseHelper responseHelper;
    private final Utility utility;

    @Value("${topic.name.producer}")
    private String topicName;

    @KafkaListener(topics = "${topic.name.producer}", groupId = "group_id")
    public GenericResponse consume(ConsumerRecord<String, String> payload){log.info("Consuming Topic: {}", topicName);
        try {
            log.info("getting order from kafka");
            log.info("Order: {}", payload.value());
            String kafkaResponse = payload.value().toString();

            ObjectMapper objectMapper = new ObjectMapper();
            OrderDto getOrder = objectMapper.readValue(kafkaResponse, OrderDto.class);

            if(Objects.isNull(getOrder))
                throw new ProductExistException("Order Not found");

            BigDecimal total = BigDecimal.valueOf(Long.parseLong(getOrder.getPrice())).multiply(BigDecimal.valueOf(Long.parseLong(getOrder.getQuantity())));
            Order order = new Order();
            log.info("setting order values");
            order.setProductId(getOrder.getProductId());
            order.setOrderId(String.valueOf(utility.generateRandomDigits(10)));
            order.setCustomerName(getOrder.getCustomerName());
            order.setCustomerPhone(getOrder.getCustomerPhone());
            order.setCustomerAddress(getOrder.getCustomerAddress());
            order.setPrice(BigDecimal.valueOf(Long.parseLong(getOrder.getPrice())));
            order.setQuantity(Integer.valueOf(getOrder.getQuantity()));
            order.setTotal(total);
            order.setDateCreated(Utility.getCurrentDate());
            log.info("inserting order, {}", order);
            Order respBody = orderRepository.save(order);
            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, respBody, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return responseHelper.getResponse(FAILED_CODE, e.getMessage(), "", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Cacheable(cacheNames = {"orderCache"}, key = "#id")
    @Override
    public GenericResponse getOrder(String id){
        try {
            log.info("getting order with Id {}", id);
            Order result = orderRepository.findOrderById(Long.valueOf(id));
            if(Objects.isNull(result)){
                return responseHelper.getResponse(ORDER_NOT_FOUND_CODE, ORDER_NOT_FOUND, null, HttpStatus.EXPECTATION_FAILED);

            }
            log.info("response body:  {}", result);
            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, result, HttpStatus.OK);
        }
        catch (Exception e){
            return responseHelper.getResponse(FAILED_CODE, e.getMessage(), null, HttpStatus.EXPECTATION_FAILED);
        }
    }


    @Override
    public GenericResponse listOrder(int pageNo, int pageSize, String startDate, String endDate){
        try {

            Page<Order> result;
            Pageable paging = PageRequest.of(pageNo, pageSize);

            if (!startDate.equals("") && !endDate.equals("")){

                Date startStringToDate=new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                log.info("StartDate: {}", startStringToDate);

                Date endStringToDate=new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
                log.info("EndDate: {}", endStringToDate);

                log.info("getting order by date");
                result = orderRepository.findOrderByDate(startStringToDate, endStringToDate, paging);
            }
            else {
                log.info("getting all order");
                result = orderRepository.findAll(paging);
            }
            log.info("{}", result);
            if (result.getTotalElements() < 1 ) {
                return responseHelper.getResponse(NOT_FOUND_CODE, ORDER_NOT_FOUND, null, HttpStatus.EXPECTATION_FAILED);
            }
            return responseHelper.getResponse(SUCCESS_CODE, SUCCESS, result, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return responseHelper.getResponse(FAILED_CODE, e.getMessage(), "", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
