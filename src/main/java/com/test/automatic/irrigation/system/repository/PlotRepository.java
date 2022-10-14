package com.test.automatic.irrigation.system.repository;

        import com.test.automatic.irrigation.system.entity.Plot;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.stereotype.Repository;

        import java.util.List;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Long> {
        @Query("SELECT DISTINCT p FROM Plot p INNER JOIN p.timeSlots t")
        List<Plot> findDetailedPlots();

}
