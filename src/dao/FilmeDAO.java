package dao;

import model.Film;

import java.util.List;

public interface FilmeDAO
{
    List<Film> findAll();
    Film findMovieById(int id);
    Film findMovieByName(String name);
}
