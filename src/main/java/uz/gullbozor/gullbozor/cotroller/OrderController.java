package uz.gullbozor.gullbozor.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.dto.OrderSaveDto;
import uz.gullbozor.gullbozor.entity.Order;
import uz.gullbozor.gullbozor.repository.OrderRepo;
import uz.gullbozor.gullbozor.service.OrderService;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;



    @PostMapping("/addOrder")
    public ResponseEntity<ApiResponse> addOrder(@RequestBody OrderSaveDto orderSaveDto) {
        ApiResponse apiResponse = orderService.addOrder(orderSaveDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400 ).body(apiResponse);
    }

    @PutMapping("/editOrder")
    public ResponseEntity<ApiResponse> editOrder(@RequestBody Long orderId, Byte status) {
        ApiResponse apiResponse = orderService.editOrder(orderId,status);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400 ).body(apiResponse);
    }

    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<ApiResponse> getOrder(@PathVariable Long orderId) {
        ApiResponse apiResponse = orderService.getOrderById(orderId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400 ).body(apiResponse);
    }

    @GetMapping("/getOrderByStatus/{statusId}/{companyId}")
    public ResponseEntity<List<Order>> getOrderById(@PathVariable Byte statusId, @PathVariable Long companyId) {
        List<Order> orderList = orderService.getByStatusId(statusId, companyId);
        return ResponseEntity.ok(orderList);
    }

    @GetMapping("getAllByCompanyId/{companyId}")
    public ResponseEntity<List<Order>> getAllByCompanyId(@PathVariable Long companyId) {
        List<Order> allByCompanyId = orderService.getAllByCompanyId(companyId);
        return ResponseEntity.ok(allByCompanyId);

    }

    @DeleteMapping("/deleteByOrderId/{orderId}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long orderId) {
        ApiResponse apiResponse = orderService.deleteById(orderId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }


}
