package com.de190293.mvc.service.impl;

import com.de190293.mvc.dto.TourDto;
import com.de190293.mvc.entity.Tour;
import com.de190293.mvc.repository.TourRepository;
import com.de190293.mvc.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourServiceImpl implements TourService {
    @Autowired
    private TourRepository tourRepository;

    @Override
    public List<TourDto> findAll(String name) {
        if(name == null || name.isBlank()){
            return tourRepository.findAll()
                    .stream()
                    .map(this::mapToTourReponse)
                    .toList();
        }else{
            return tourRepository.findAllByTourNameContainingIgnoreCaseOrderByTourNameAsc(name)
                    .stream()
                    .map(this::mapToTourReponse)
                    .toList();
        }
    }

    @Override
    public boolean deleteTour(Integer id) {
        if(tourRepository.findById(id).isEmpty()){
            return false;
        }
        tourRepository.deleteById(id);
        return true;
    }

    @Override
    public void addTour(TourDto tourDto) {
        Tour tour = new Tour();
        tour.setTourName(tourDto.getTourName());
        tour.setDestination(tourDto.getDestination());
        tour.setCapacity(tourDto.getCapacity());
        tour.setDuration(tourDto.getDuration());
        tour.setPrice(tourDto.getPrice());
        tour.setStartDate(tourDto.getStartDate());
        tour.setStatus(tourDto.getStatus());

        tourRepository.save(tour);
    }

    @Override
    public boolean existedTourName(String tourName) {
        return tourRepository.existsByTourNameIgnoreCase(tourName);
    }

    @Override
    public TourDto getTour(Integer id) {
        return tourRepository.findById(id)
                .map(this::mapToTourReponse)
                .orElseThrow(() -> new RuntimeException("Tour ID: " + id + " not existed in system"));
    }

    private TourDto mapToTourReponse(Tour tour){
        TourDto tourResponse = new TourDto();
        tourResponse.setId(tour.getId());
        tourResponse.setTourName(tour.getTourName());
        tourResponse.setCapacity(tour.getCapacity());
        tourResponse.setDestination(tour.getDestination());
        tourResponse.setPrice(tour.getPrice());
        tourResponse.setStartDate(tour.getStartDate());
        tourResponse.setDuration(tour.getDuration());
        tourResponse.setStatus(tour.getStatus());
        return tourResponse;
    }
}
