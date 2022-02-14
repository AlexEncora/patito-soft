package com.patitosoft.encora.patitosoftapp.service;

import com.patitosoft.encora.patitosoftapp.domain.Position;
import com.patitosoft.encora.patitosoftapp.repository.EmployeePositionRepository;
import com.patitosoft.encora.patitosoftapp.repository.PositionRepository;
import com.patitosoft.encora.patitosoftapp.resource.DTOEachPosition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final EmployeePositionRepository employeePositionRepository;

    public List<DTOEachPosition> getEmployeesEachPosition() {

        List<DTOEachPosition> dtoEachPositions = new LinkedList<>();
        List<Position> positions = positionRepository.findAll();
        if(!CollectionUtils.isEmpty(positions)){
            positions.forEach(position -> {
                DTOEachPosition dtoEachPosition = new DTOEachPosition();
                dtoEachPosition.setNamePosition(position.getName());
                dtoEachPosition.setEmployeesCount(employeePositionRepository
                        .countByPosition_IdAndCurrentPosition(position.getId(),true));
                dtoEachPositions.add(dtoEachPosition);
            });
        }

        return dtoEachPositions;
    }
}
