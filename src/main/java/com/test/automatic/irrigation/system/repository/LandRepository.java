package com.test.automatic.irrigation.system.repository;

import com.test.automatic.irrigation.system.entity.Land;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandRepository extends JpaRepository <Land, Long>{

}
