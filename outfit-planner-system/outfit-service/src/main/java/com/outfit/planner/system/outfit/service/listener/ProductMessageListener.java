package com.outfit.planner.system.outfit.service.listener;

import com.outfit.planner.system.outfit.service.dataaccess.product.entity.ProductEntity;

public interface ProductMessageListener {

    void productCreated(ProductEntity productEntity);

}
