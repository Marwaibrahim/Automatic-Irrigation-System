package com.test.automatic.irrigation.system.repository;

import com.test.automatic.irrigation.system.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    @Query("SELECT t FROM TimeSlot t JOIN t.plot p JOIN p.sensor s WHERE t.status = 'PENDING' AND t.startTime BETWEEN ?1 AND ?2")
    List<TimeSlot> findReadySlots(Timestamp from, Timestamp to);

}

