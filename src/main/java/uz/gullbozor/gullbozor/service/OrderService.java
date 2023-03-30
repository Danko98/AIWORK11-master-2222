package uz.gullbozor.gullbozor.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.dto.OrderSaveDto;
import uz.gullbozor.gullbozor.entity.Order;
import uz.gullbozor.gullbozor.repository.OrderRepo;

import java.util.List;
import java.util.Optional;


@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    public ApiResponse addOrder(OrderSaveDto orderSaveDto) {

        if (orderRepo.existsById(orderSaveDto.getOrderId())) {
            Optional<Order> OptionalOrder = orderRepo.findById(orderSaveDto.getOrderId());
            Order order = OptionalOrder.get();
            order.setOrderHolderName(orderSaveDto.getOrderOwnerName());
            order.setOrderHolderPhone(orderSaveDto.getOrderOwnerPhone());

            orderRepo.save(order);

            return new ApiResponse("Buyurtma saqlandi",true);
        }else {
            return new ApiResponse("Bu Id lik buyurtma topilmadi",false);
        }

    }

    public ApiResponse editOrder(Long orderId, Byte status) {

        if (orderRepo.existsById(orderId)) {
            Optional<Order> OptionalOrder = orderRepo.findById(orderId);
            Order order = OptionalOrder.get();
            order.setStatus(status);

            orderRepo.save(order);

            return new ApiResponse("Buyurtma statusi o'zgartirildi",true);
        }else {
            return new ApiResponse("Bu Id lik buyurtma topilmadi",false);
        }
    }

    public ApiResponse getOrderById(Long orderId) {

        if (orderRepo.existsById(orderId)) {
            Optional<Order> OptionalOrder = orderRepo.findById(orderId);
            Order order = OptionalOrder.get();
            return new ApiResponse(order);
        }else {
            return new ApiResponse("Bu Id lik buyurtma topilmadi",false);
        }
    }


    public List<Order> getByStatusId(Byte statusId, Long companyId) {

        List<Order> orderList = orderRepo.findAllByCompanyIdAndStatus(companyId,statusId);

        return orderList;

    }

    public List<Order> getAllByCompanyId(Long companyId) {
        return orderRepo.findAllByCompanyId(companyId);
    }


    public ApiResponse deleteById(Long orderId) {



        if (orderRepo.existsById(orderId)) {
            orderRepo.deleteById(orderId);
            return new ApiResponse("Buyurtma o'chirildi",true);

        }else {
            return new ApiResponse("Buyurtma topilamdi",false);

        }
    }

}
