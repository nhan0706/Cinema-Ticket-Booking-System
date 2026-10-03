package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.FnbItemEntity;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FnbItemRepository;

/**
 * Service: Xử lý logic nghiệp vụ CRUD cho Bắp nước / F&B (FnbItem)
 */
@Service
public class FnbItemService {

    private final FnbItemRepository fnbItemRepository;

    @Autowired
    public FnbItemService(FnbItemRepository fnbItemRepository) {
        this.fnbItemRepository = fnbItemRepository;
    }

    public List<FnbItemEntity> getAllFnbItems() {
        return fnbItemRepository.findAll();
    }

    public FnbItemEntity getFnbItemById(Long id) {
        return fnbItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm F&B", "id", id));
    }

    public List<FnbItemEntity> getFnbItemsByCategory(String category) {
        return fnbItemRepository.findByCategory(category);
    }

    public FnbItemEntity createFnbItem(FnbItemEntity item) {
        if (item.getItemName() == null || item.getItemName().trim().isEmpty()) {
            throw new BadRequestException("Tên sản phẩm F&B không được để trống!");
        }
        if (item.getBasePrice() == null || item.getBasePrice() <= 0) {
            throw new BadRequestException("Giá sản phẩm phải lớn hơn 0!");
        }
        if (fnbItemRepository.existsByItemName(item.getItemName())) {
            throw new DuplicateResourceException("Sản phẩm '" + item.getItemName() + "' đã tồn tại!");
        }
        return fnbItemRepository.save(item);
    }

    public FnbItemEntity updateFnbItem(Long id, FnbItemEntity itemDetails) {
        FnbItemEntity existing = getFnbItemById(id);
        if (itemDetails.getItemName() == null || itemDetails.getItemName().trim().isEmpty()) {
            throw new BadRequestException("Tên sản phẩm F&B không được để trống!");
        }
        existing.setItemName(itemDetails.getItemName());
        existing.setCategory(itemDetails.getCategory());
        existing.setDescription(itemDetails.getDescription());
        existing.setImageUrl(itemDetails.getImageUrl());
        existing.setBasePrice(itemDetails.getBasePrice());
        existing.setIsActive(itemDetails.getIsActive());
        return fnbItemRepository.save(existing);
    }

    public void deleteFnbItem(Long id) {
        FnbItemEntity item = getFnbItemById(id);
        fnbItemRepository.delete(item);
    }
}
