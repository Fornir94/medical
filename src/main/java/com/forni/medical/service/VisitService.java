package com.forni.medical.service;

import com.forni.medical.exception.visitexception.VisitDateException;
import com.forni.medical.exception.visitexception.VisitExistsException;
import com.forni.medical.exception.visitexception.VisitFullException;
import com.forni.medical.exception.visitexception.VisitNotFoundException;
import com.forni.medical.mapper.VisitMapper;
import com.forni.medical.model.dto.VisitCreationDTO;
import com.forni.medical.model.dto.VisitDTO;
import com.forni.medical.model.entity.Patient;
import com.forni.medical.model.entity.Visit;
import com.forni.medical.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    public VisitDTO addVisit(VisitCreationDTO visitCreationDTO){
        Optional<Visit> visit1 = visitRepository.findByDate(visitCreationDTO.getDate());
        if (visit1.isPresent()){
            throw new VisitExistsException("Visit with this date already exists");
        }
        if (visitCreationDTO.getDate().isBefore(LocalDateTime.now()) || visitCreationDTO.getDate().getMinute() % 15!=0){
            throw new VisitDateException("Visit with this date is before then actual, or time is different than a full quarter of an hour");
        }
        Visit visit= visitMapper.toEntity(visitCreationDTO);
        return visitMapper.toDto(visitRepository.save(visit));
    }

    public List<VisitDTO> getAllVisits(){
        return visitRepository.findAll().stream()
                .map(visitMapper::toDto)
                .collect(Collectors.toList());
    }

    public void addPatientToVisit(Long id, Patient patient){
        Visit visit = visitRepository.findById(id).orElseThrow(()-> new VisitNotFoundException("Visit not found"));
        if (visit.getPatient()!=null){
            throw new VisitFullException("This Visit is full");
        }
        visit.setPatient(patient);
        visitRepository.save(visit);
    }

    public VisitDTO findVisit(Long id){
        return visitRepository.findById(id).stream()
                .map(visitMapper::toDto)
                .findFirst().orElseThrow(()->new VisitNotFoundException("Visit not found"));
    }

    public void deleteVisit(Long id){
        Visit visit = visitRepository.findById(id).orElseThrow(()-> new VisitNotFoundException("Visit not found"));
        visitRepository.delete(visit);
    }
}
