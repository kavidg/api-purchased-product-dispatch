package co.com.asgard.core.dto;

import co.com.asgard.core.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOutboundStatusUpdateDTO {
    private Long id;
    private OrderStatus status;
}