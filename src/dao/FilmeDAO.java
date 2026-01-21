package dao;

import model.Film;

import java.util.List;

public interface FilmeDAO
{
    List<Film> findAll();
    Film findMovieById(int id);
    Film findMovieByName(String name);

    boolean addMovieSimple(String name);        //TODO return MovieId
    boolean finishSimpleMovieEntry(int id,int fsk, int dauer, boolean dreid, String beschreibung,int erscheinungsjahr,List<String> genres,List<String> sprachen);
    boolean addMovie(String name,int fsk, int dauer, boolean dreid, String beschreibung,int erscheinungsjahr,List<String> genres,List<String> sprachen);

    boolean updateFsk(int id, int fsk);
    boolean updateDauer(int id, int min);
    boolean updatedreid(int id, boolean dreid);
    boolean updateBeschreibung(int id, String b);
    boolean updateRelease(int id, int jahr);
    int updateGenres(int id,String... g);
    int updateLanguages(int id, String... l);


    boolean checkFsk(int jahr);
    List<String> getGenres(int id);
    List<String>getLanguages(int id);
}
