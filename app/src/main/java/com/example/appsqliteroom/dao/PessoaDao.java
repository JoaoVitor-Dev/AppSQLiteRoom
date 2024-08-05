package com.example.appsqliteroom.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.appsqliteroom.entities.Pessoa;

import java.util.List;

@Dao
public interface PessoaDao
{
    @Insert
    public Long adicionar(Pessoa p);

    @Query("SELECT * FROM pessoa")
    public List<Pessoa> listar();

    @Update
    public int atualizar(Pessoa p);

    @Delete
    public int excluir(Pessoa p);
}
