package org.example.htmldesgin.service;

import org.example.htmldesgin.dao.mapper.TrainerWorkingHoursMapper;
import org.example.htmldesgin.utils.TrainerWorkingHours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerWorkingHoursService {
    @Autowired
    private TrainerWorkingHoursMapper trainerWorkingHoursMapper;

    public List<TrainerWorkingHours> getAllWorkingHours() {
        return trainerWorkingHoursMapper.selectAll();
    }

    public boolean updateWorkingHours(String username, String month, Float hours) {
        return trainerWorkingHoursMapper.updateHours(username, month, hours) > 0;
    }

    public boolean addWorkingHours(String username) {
        return trainerWorkingHoursMapper.addTrainerWorkingHours(username) == 1;
    }

    public boolean deleteWorkingHours(List<String> username) {
        return trainerWorkingHoursMapper.deleteTrainerWorkingHours(username) == 1;
    }
}
