package com.chinaka.task.order.consumer.mintyn.controller;


import com.chinaka.task.order.consumer.mintyn.service.OrderService;
import com.chinaka.task.order.consumer.mintyn.dto.GenericResponse;
import com.chinaka.task.order.consumer.mintyn.dto.OrderDto;
import com.chinaka.task.order.consumer.mintyn.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping(Constants.ORDER_BASE_URL+"/order")
public class OrderController {
    private final OrderService orderService;
    @GetMapping({"/{id}"})
    public ResponseEntity<GenericResponse> getOrder(@PathVariable String id) {

        GenericResponse resp = orderService.getOrder(id);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }
    @GetMapping("")
    public ResponseEntity<GenericResponse> listOrder(
                                                        @RequestParam (defaultValue = "0")int pageNo,
                                                         @RequestParam(defaultValue = "10") int pageSize,
                                                         @RequestParam(defaultValue = "") String startDate,
                                                         @RequestParam(defaultValue = "") String endDate
                                                    ) {
        GenericResponse resp = orderService.listOrder(pageNo, pageSize, startDate, endDate);
        return new ResponseEntity<>(resp, resp.getHttpStatus());
    }
}
