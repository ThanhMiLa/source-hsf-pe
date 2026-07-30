package com.de190293.mvc.service;

import com.de190293.mvc.dto.TourDto;

import java.util.List;

public interface TourService {
    List<TourDto> findAll(String name);
    boolean deleteTour(Integer id);
    void addTour(TourDto tourDto);
    boolean existedTourName(String tourName);
    TourDto getTour(Integer id);
}
