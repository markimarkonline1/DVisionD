package dao;

import db.DbConnect;
import model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAOImpl implements FilmeDAO
{
    private Connection con = DbConnect.getInstance().connection();

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
        return null;
    }

    @Override
    public Film findMovieByName(String name)
    {
        return null;
    }



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
