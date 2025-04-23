package co.com.asgard.core.service;

import co.com.asgard.core.dto.ProductOutboundRequestDTO;
import co.com.asgard.core.dto.ProductOutboundResponseDTO;
import co.com.asgard.core.dto.ProductOutboundStatusUpdateDTO;

public interface ProductOutboundService {
    ProductOutboundResponseDTO registerOutbound(ProductOutboundRequestDTO dto);
    void updateStatus(ProductOutboundStatusUpdateDTO dto);
    
}