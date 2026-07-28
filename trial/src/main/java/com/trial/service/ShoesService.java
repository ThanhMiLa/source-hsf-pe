package com.trial.service;

import com.trial.dto.ShoesDTO;

import java.util.List;

public interface ShoesService {
    List<ShoesDTO> findAllShoes(String shoesName);
    void deleteShoes(Integer id);
    void addShoes(ShoesDTO shoesDTO);
    ShoesDTO getShoes(Integer id);
    boolean existedShoesNo(String shoesNo);
}
