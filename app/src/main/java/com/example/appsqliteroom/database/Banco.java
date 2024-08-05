package com.example.appsqliteroom.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.appsqliteroom.dao.PessoaDao;
import com.example.appsqliteroom.entities.Pessoa;

@Database(entities = {Pessoa.class}, version = 1)
public abstract class Banco extends RoomDatabase
{
    public abstract PessoaDao pessoaDao();
}
