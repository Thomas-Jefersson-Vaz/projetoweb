package com.mikrolabs;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseManager {

    private static final HikariDataSource dataSource;

    static {
        Dotenv dotenv = Dotenv.configure()
        .directory("/")
        .filename(".env")
        .load();
        
        HikariConfig config = new HikariConfig();

        //Connection Settings
        config.setJdbcUrl(dotenv.get("DB_URL"));
        config.setUsername(dotenv.get("DB_USER"));
        config.setPassword(dotenv.get("DB_PASSWORD"));
        config.setDriverClassName("org.postgresql.Driver");

        //Pool Sizing
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30_000);
        config.setIdleTimeout(600_000);
        config.setMaxLifetime(1_800_000);

        //Postgre tunning
        config.setPoolName("PostgresHikariPool");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("reWriteBatchedInserts", "true");


        dataSource = new HikariDataSource(config);

    }

    public static DataSource getDataSource() {
        return dataSource;
    }


    public static void close() {
        if (dataSource != null) dataSource.close();
    }

    
}
