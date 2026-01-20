package model;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Film
{
    private int id = -1;
    private String filmname;
    private int fsk;
    private int dauer;
    private boolean dreid;
    private String beschreibung;
    private int erscheinungsjahr;
    private List<String> genres;
    private List<String> sprachen;


    // ---------------- Constructors --------------------------

    public Film(){
        this.id = -100;
        this.filmname = "";
        this.fsk = -100;
        this.dauer = -100;
        this.dreid = false;
        this.beschreibung = "";
        this.erscheinungsjahr = -100;
        this.genres = new ArrayList<String>();
        this.sprachen = new ArrayList<String>();
    }

    public Film(int id, String filmname, int fsk, int dauer, boolean dreid, String beschreibung, int erscheinungsjahr, List<String> genres, List<String> sprachen)
    {
        this.id = id;
        this.filmname = filmname;
        this.fsk = fsk;
        this.dauer = dauer;
        this.dreid = dreid;
        this.beschreibung = beschreibung;
        this.erscheinungsjahr = erscheinungsjahr;
        this.genres = genres;
        this.sprachen = sprachen;
    }

    public Film(int id, String filmname)
    {
        this.id = id;
        this.filmname = filmname;
    }

    public Film(String filmname)
    {
        this.filmname = filmname;
    }

// -------------- getter & setter ----------------------------

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFilmname()
    {
        return filmname;
    }

    public void setFilmname(String filmname)
    {
        this.filmname = filmname;
    }

    public int getFsk()
    {
        return fsk;
    }

    public void setFsk(int fsk)
    {
        this.fsk = fsk;
    }

    public int getDauer()
    {
        return dauer;
    }

    public void setDauer(int dauer)
    {
        this.dauer = dauer;
    }

    public boolean isDreid()
    {
        return dreid;
    }

    public void setDreid(boolean dreid)
    {
        this.dreid = dreid;
    }

    public String getBeschreibung()
    {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung)
    {
        this.beschreibung = beschreibung;
    }

    public int getErscheinungsjahr()
    {
        return erscheinungsjahr;
    }

    public void setErscheinungsjahr(int erscheinungsjahr)
    {
        this.erscheinungsjahr = erscheinungsjahr;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public void setGenres(List<String> genres)
    {
        this.genres = genres;
    }

    public List<String> getSprachen()
    {
        return sprachen;
    }

    public void setSprachen(List<String> sprachen)
    {
        this.sprachen = sprachen;
    }

    // ------------------ equals & hashcode --------------------


    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && fsk == film.fsk && dauer == film.dauer && dreid == film.dreid && erscheinungsjahr == film.erscheinungsjahr && Objects.equals(filmname, film.filmname) && Objects.equals(beschreibung, film.beschreibung) && Objects.equals(genres, film.genres) && Objects.equals(sprachen, film.sprachen);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, filmname, fsk, dauer, dreid, beschreibung, erscheinungsjahr, genres, sprachen);
    }

// ------------------- toString --------------------------------


    @Override
    public String toString()
    {
        return "Film " +
                "id = " + id +
                "\nFilmname = " + filmname +
                "\nFsk = " + fsk + " Jahre" +
                "\nDauer = " + dauer + "min" +
                "\n3d = " + dreid +
                "\nBeschreibung = " + beschreibung +
                "\nErscheinungsjahr = " + erscheinungsjahr +
                "\nGenres = " + genres +
                "\nSprachen = " + sprachen +
                "\n\n"
                ;
    }
}