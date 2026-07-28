package com.trial.service.impl;

import com.trial.dto.ShoesTypeDTO;
import com.trial.entity.ShoesType;
import com.trial.repository.ShoesTypeRepository;
import com.trial.service.ShoesTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoesTypeServiceImpl implements ShoesTypeService {

    private final ShoesTypeRepository shoesTypeRepository;

    public ShoesTypeServiceImpl(ShoesTypeRepository shoesTypeRepository) {
        this.shoesTypeRepository = shoesTypeRepository;
    }

    @Override
    public List<ShoesTypeDTO> findAllShoesType() {
        List<ShoesType> shoesTypeList = shoesTypeRepository.findAll();
        return shoesTypeList.stream()
                .map(shoesType -> {
                    return new ShoesTypeDTO(shoesType.getTypeCode(), shoesType.getTypeName());
                })
                .toList();
    }
}
