package com.trial.service;

import com.trial.dto.ShoesTypeDTO;
import com.trial.entity.ShoesType;

import java.util.List;

public interface ShoesTypeService {
    List<ShoesTypeDTO> findAllShoesType();
}
