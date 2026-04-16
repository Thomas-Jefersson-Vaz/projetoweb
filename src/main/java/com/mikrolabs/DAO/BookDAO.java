package com.mikrolabs.DAO;

import com.mikrolabs.entities.Book;
import com.mikrolabs.entities.Location;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterBeanMapper(Location.class)
public interface BookDAO extends BaseDAO<Book, Integer>{
    @SqlUpdate("INSERT INTO bookings (user_id, location_id, booked_at) " +
            "VALUES (:userId, :locationId, :bookedAt)")
    @GetGeneratedKeys
    int insert(@BindBean Book book);
}
