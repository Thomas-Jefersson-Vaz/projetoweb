package com.mikrolabs.DAO;

import org.jdbi.v3.sqlobject.customizer.Define;

import java.util.List;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface BaseDAO<T, K> {
        @SqlQuery("SELECT * FROM <table_name> WHERE <pk_col> = :id")
        T findById(
                        @Define("table_name") String tableName,
                        @Define("pk_col") String pk_col,
                        @Bind("id") K id);

        @SqlQuery("SELECT * FROM <table_name>")
        List<T> findAll(
                        @Define("table_name") String tableName);

}
