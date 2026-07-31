package com.trial.service.impl;

import com.trial.dto.ShoesDTO;
import com.trial.entity.Shoes;
import com.trial.repository.ShoesRepository;
import com.trial.repository.ShoesTypeRepository;
import com.trial.service.ShoesService;
import com.trial.util.GenericMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoesServiceImpl implements ShoesService {

    private final ShoesRepository shoesRepository;
    private final ShoesTypeRepository shoesTypeRepository;

    public ShoesServiceImpl(ShoesRepository shoesRepository, ShoesTypeRepository shoesTypeRepository) {
        this.shoesRepository = shoesRepository;
        this.shoesTypeRepository = shoesTypeRepository;
    }

    @Override
    public List<ShoesDTO> findAllShoes(String shoesName) {
        List<Shoes> shoesList = new ArrayList<>();
        if(shoesName == null || shoesName.isBlank()) {
            shoesList = shoesRepository.findAll();
        }else{
            shoesList = shoesRepository.findByShoesNameContainingIgnoreCase(shoesName);
        }

        return shoesList.stream()
                .map(shoes -> {
                    ShoesDTO shoesDTO = GenericMapper.map(shoes, ShoesDTO.class);
                    shoesDTO.setType(shoes.getShoesType().getTypeName());
                    return shoesDTO;
                })
                .toList();
    }

    @Override
    public void deleteShoes(Integer id) {
        if(shoesRepository.findById(id).isPresent()){
            shoesRepository.deleteById(id);
        }
    }

    @Override
    public void addShoes(ShoesDTO shoesDTO){
        Shoes shoes = GenericMapper.map(shoesDTO, Shoes.class);
        shoes.setShoesType(shoesTypeRepository.findByTypeName(shoesDTO.getType()));
        shoesRepository.save(shoes);
    }

    @Override
    public ShoesDTO getShoes(Integer id) {
        Shoes shoes = shoesRepository.findById(id).orElse(null);
        if(shoes == null){
            return null;
        }

        ShoesDTO shoesDTO = GenericMapper.map(shoes, ShoesDTO.class);
        shoesDTO.setType(shoes.getShoesType().getTypeName());

        return shoesDTO;
    }

    @Override
    public boolean existedShoesNo(String shoesNo) {
        return shoesRepository.findByShoesNo(shoesNo).isPresent();
    }


}
