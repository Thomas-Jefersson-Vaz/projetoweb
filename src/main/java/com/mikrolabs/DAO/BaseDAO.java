package com.mikrolabs.DAO;

import java.sql.SQLException;

public interface BaseDAO<T, K>{
    int register(T entidade) throws SQLException;
}
