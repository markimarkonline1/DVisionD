package dao;

import model.Film;

import java.util.List;

public interface FilmeDAO
{
    List<Film> findAll();
    Film findMovieById(int id);
    Film findMovieByName(String name);

    boolean addMovieSimple(String name);
    boolean finishSimpleMovieEntry(int id,int fsk, int dauer, boolean dreid, String beschreibung,int erscheinungshahr);

    boolean updateFsk(int id, int fsk);
    boolean updateDauer(int id, int min);
    boolean updatedreid(int id, boolean dreid);
    boolean updateBeschreibung(int id, String b);
    boolean updateRelease(int id, int jahr);


    boolean checkFsk(int Freigabe);
    boolean checkErscheinungsjahr(int j);

    List<String>getGenres(int id);
    List<String>getLanguages(int id);
}
