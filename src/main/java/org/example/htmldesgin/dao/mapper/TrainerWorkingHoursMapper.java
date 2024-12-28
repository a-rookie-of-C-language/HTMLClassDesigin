package org.example.htmldesgin.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.example.htmldesgin.utils.TrainerWorkingHours;

import java.util.List;

@Mapper
public interface TrainerWorkingHoursMapper {
    @Select("select * from trainerWorkingHours")
    List<TrainerWorkingHours> selectAll();

    @Update("UPDATE trainerWorkingHours SET `${month}` = #{hours} " +
            "WHERE username = #{username}")
    int updateHours(@Param("username") String username,
                    @Param("month") String month,
                    @Param("hours") Float hours);
    @Insert("INSERT INTO trainerWorkingHours(username) VALUES (#{username})")
    int addTrainerWorkingHours(String username);

    int deleteTrainerWorkingHours(List<String> username);
}
