package com.mikrolabs.DAO;

import java.time.LocalDate;
import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.mikrolabs.entities.Location;

@RegisterBeanMapper(Location.class)
public interface LocationDAO extends BaseDAO<Location, Integer> {

    @SqlQuery("SELECT * FROM locations ORDER BY name")
    List<Location> findAll();

    @SqlQuery("SELECT * FROM locations WHERE LOWER(name) LIKE LOWER(:name) ORDER BY name")
    List<Location> searchByName(@Bind("name") String name);

    @SqlQuery("""
                SELECT * FROM locations\s
                WHERE (:name IS NULL OR name ILIKE CONCAT('%', CAST(:name AS TEXT), '%'))
                  AND (CAST(:startDate AS DATE) IS NULL OR start_date >= CAST(:startDate AS DATE))
                  AND (CAST(:endDate AS DATE) IS NULL OR end_date <= CAST(:endDate AS DATE))
            """)
    List<Location> searchComplex(
            @Bind("name") String name,
            @Bind("startDate") LocalDate start,
            @Bind("endDate") LocalDate end);

    @SqlQuery("SELECT * FROM locations WHERE LOWER(continent) = LOWER(:continent) ORDER BY name")
    List<Location> findByContinent(@Bind("continent") String continent);

    @SqlUpdate("INSERT INTO locations (name, country, continent, description, price, image_url, start_date, end_date) "
            +
            "VALUES (:name, :country, :continent, :description, :price, :imageUrl, :startDate, :endDate)")
    boolean insert(@Bind("name") String name,
            @Bind("country") String country,
            @Bind("continent") String continent,
            @Bind("description") String description,
            @Bind("price") java.math.BigDecimal price,
            @Bind("imageUrl") String imageUrl,
            @Bind("startDate") java.time.LocalDate startDate,
            @Bind("endDate") java.time.LocalDate endDate);

    @SqlUpdate("DELETE FROM locations WHERE id = :id")
    boolean deleteById(@Bind("id") int id);
}
