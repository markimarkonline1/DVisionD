package dao;

import db.DbConnect;
import model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FilmeDAOImpl implements FilmeDAO
{
    private final Connection con = DbConnect.getInstance().connection();

    @Override
    public List<Film> findAll(){

        var filmlist = new ArrayList<Film>();
        try (var ps = con.prepareStatement("SELECT * FROM t_filme"))
        {
            buildMovie(ps,filmlist);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return filmlist;
    }

    @Override
    public Film findMovieById(int id)
    {
        var filmlist = new ArrayList<Film>();
        try(var ps = con.prepareStatement("SELECT * FROM t_filme WHERE Id=?"))
        {

            ps.setInt(1,id);
            buildMovie(ps,filmlist);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        if(filmlist.isEmpty()){
            System.out.println("Film nicht gefunden");
            return null;
        }else{
        return filmlist.getFirst();}
    }

    @Override
    public Film findMovieByName(String name)
    {
        var filmlist = new ArrayList<Film>();

        try(var ps = con.prepareStatement("SELECT * FROM t_filme WHERE Name LIKE ?"))
        {
            ps.setString(1, "%" + name + "%");
            buildMovie(ps, filmlist);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        if(filmlist.isEmpty()){
            System.out.println("Film nicht gefunden");
            return null;
        }else{
            return filmlist.getFirst();}
    }

    @Override
    public boolean addMovieSimple(String name)
    {
        if(name == "" || name == null){
            System.out.println("Gib einen Filmtitel ein");
            return false;
        }

        try (var ps = con.prepareStatement("SELECT Name FROM t_filme WHERE Name=?"))
        {
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("Film schon vorhanden");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        var q = """
                        INSERT INTO t_filme (Name,Fsk,Dauer,3d,Beschreibung,Jahr)
                        VALUES (?,?,?,?,?,?)
                    """;
        try(PreparedStatement ps = con.prepareStatement(q);)
        {
            ps.setString(1,name);
            ps.setInt(2,-1);
            ps.setInt(3,-1);
            ps.setBoolean(4,false);
            ps.setString(5,"");
            ps.setInt(6,-1);

            int n = ps.executeUpdate();
            return n == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
            // TODO add genre & sprachen
    @Override
    public boolean finishSimpleMovieEntry(int id, int fsk, int dauer, boolean dreid, String beschreibung, int erscheinungsjahr)
    {
        Film film = findMovieById(id);
        if(film == null){
            System.out.println("Film nicht gefunden");
            return false;
        }
        film.setFsk(fsk);
        film.setDauer(dauer);
        film.setDreid(dreid);
        film.setBeschreibung(beschreibung);
        film.setErscheinungsjahr(erscheinungsjahr);

        return true;
    }

    @Override
    public boolean updateFsk(int id, int fsk)
    {
        return false;
    }

    @Override
    public boolean updateDauer(int id, int min)
    {
        return false;
    }

    @Override
    public boolean updatedreid(int id, boolean dreid)
    {
        return false;
    }

    @Override
    public boolean updateBeschreibung(int id, String b)
    {
        return false;
    }

    @Override
    public boolean updateRelease(int id, int jahr)
    {
        return false;
    }

    @Override
    public boolean checkFsk(int Freigabe)
    {
        return false;
    }

    @Override
    public boolean checkErscheinungsjahr(int j)
    {
        return false;
    }

    @Override
    public List<String> getGenres(int id)
    {
        return List.of();
    }

    @Override
    public List<String> getLanguages(int id)
    {
        return List.of();
    }

    //TODO add genre & sprachen
    private static void buildMovie(PreparedStatement ps, List<Film> filme) throws SQLException
    {
        var rs = ps.executeQuery();
        while(rs.next()){
            Film f = new Film();
            f.setId(rs.getInt("Id"));
            f.setFilmname(rs.getString("Name"));
            f.setFsk(rs.getInt("Fsk"));
            f.setDauer(rs.getInt("Dauer"));
            f.setDreid((rs.getBoolean("3d")));
            f.setBeschreibung(rs.getString("Beschreibung"));
            f.setErscheinungsjahr(rs.getInt("Jahr"));

            filme.add(f);
        }

    }
}
