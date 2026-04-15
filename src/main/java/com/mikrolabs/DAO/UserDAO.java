package com.mikrolabs.DAO;

import java.time.LocalDate;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.mikrolabs.entities.User;

@RegisterBeanMapper(User.class)
public interface UserDAO extends BaseDAO<User, String> {

        @SqlUpdate("INSERT INTO users (name, email, password, role) VALUES(:name, :email, :password, :role)")
        Boolean registerUser(
                        @Bind("name") String name,
                        @Bind("email") String email,
                        @Bind("password") String password,
                        @Bind("role") String role);

        @SqlUpdate("UPDATE users SET name = :name, data_nascimento = :data_nascimento, nacionalidade = :nacionalidade, num_telefone = :num_telefone, cidade = :cidade, bio = :bio, assento = :assento, comida = :comida, classe = :classe, moeda = :moeda WHERE email = :email")
        Boolean PatchUser(
                        @Bind("assento") String assento,
                        @Bind("bio") String bio,
                        @Bind("cidade") String cidade,
                        @Bind("classe") String classe,
                        @Bind("comida") String comida,
                        @Bind("data_nascimento") LocalDate data_nascimento,
                        @Bind("moeda") String moeda,
                        @Bind("nacionalidade") String nacionalidade,
                        @Bind("name") String name,
                        @Bind("num_telefone") String num_telefone,
                        @Bind("email") String email);

}
