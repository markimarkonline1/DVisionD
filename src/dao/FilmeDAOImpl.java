package dao;

import db.DbConnect;
import model.Film;

import java.sql.*;
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
        if (!checkFsk(fsk))
        {
            System.out.println("Gib eine gültige Altersfreigabe ein");
            return false;
        }
            try (var ps = con.prepareStatement("UPDATE t_filme SET Fsk = ? WHERE Id = ?"))
            {
                ps.setInt(1, fsk);
                ps.setInt(2, id);
                int n = ps.executeUpdate();
                if(n==1)
                {
                    return true;
                } else
                {
                    System.out.println("Film nicht gefunden");
                    return false;
                }

            }catch (SQLException e)
            {
                throw new RuntimeException(e);
            }

    }

    @Override
    public boolean updateDauer(int id, int min)
    {
        try(var ps = con.prepareStatement("UPDATE t_filme SET Dauer = ? WHERE Id = ?"))
        {
            ps.setInt(1,min);
            ps.setInt(2,id);
            int n = ps.executeUpdate();
            if(n==1){
                return true;
            }else{
                System.out.println("Film nicht gefunden");
                return false;
            }

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updatedreid(int id, boolean dreid)
    {
        try(var ps = con.prepareStatement("UPDATE t_filme SET 3d = ? WHERE Id = ?"))
        {
            ps.setBoolean(1,dreid);
            ps.setInt(2,id);
            int n = ps.executeUpdate();
            if(n==1){
                return true;
            }else{
                System.out.println("Film nicht gefunden");
                return false;
            }

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateBeschreibung(int id, String b)
    {
        try(var ps = con.prepareStatement("UPDATE t_filme SET Beschreibung = ? WHERE Id = ?"))
        {
            ps.setString(1,b);
            ps.setInt(2,id);
            int n = ps.executeUpdate();
            if(n==1){
                return true;
            }else{
                System.out.println("Film nicht gefunden");
                return false;
            }

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateRelease(int id, int jahr)
    {
        if(jahr < 1895 || jahr > java.time.Year.now().getValue()){
            System.out.println("Gib ein gültiges ErscheinungsJahr ein");
            return false;
        }

        try(var ps = con.prepareStatement("UPDATE t_filme SET Jahr = ? WHERE Id = ?"))
        {
            ps.setInt(1,jahr);
            ps.setInt(2,id);
            int n = ps.executeUpdate();
            if(n==1){
                return true;
            }else{
                System.out.println("Film nicht gefunden");
                return false;
            }

        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateGenres(int id, String... g)
    {
        // ---------- Genres in Ids übersetzen --------------
        List<String> genreList = List.of(g);
        ArrayList<Integer> ids = new ArrayList<>();

        for (String genre : genreList)
        {
            try (var ps = con.prepareStatement("SELECT id FROM t_genres WHERE Genre = ?"))
            {
                ps.setString(1, genre);
                var rs = ps.executeQuery();
                if (rs.next())
                {
                    ids.add(rs.getInt("id"));
                } else {
                    System.out.println("Genre nicht gefunden: " + genre);
                    return 0;
                }
            } catch (SQLException e)
            { throw new RuntimeException(e); }
        }
        // ------- alte Genreeinträge löschen ----------------
        try (var ps = con.prepareStatement( "DELETE FROM vt_filme_genres WHERE film_id = ?"))
        {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e)
        { throw new RuntimeException(e); }

        //--------- neue Genres eintragen -----------------------

        int inserted = 0;
        for (int genreId : ids)
        {
            try (var ps = con.prepareStatement( "INSERT INTO vt_filme_genres (film_id, genre_id) VALUES (?, ?)"))
            {
                ps.setInt(1, id);
                ps.setInt(2, genreId);
                inserted += ps.executeUpdate();
            } catch (SQLException e)
            { throw new RuntimeException(e); }
        }

        return inserted;
    }

    @Override
    public int updateLanguages(int id, String... l)
    {
        List<String> languageList = List.of(l);
        ArrayList<Integer> ids = new ArrayList<>();
        // 1. Sprach-Namen → IDs auflösen
        for (String lang : languageList)
        {
            try (var ps = con.prepareStatement("SELECT id FROM t_sprachen WHERE sprache = ?"))
            {
                ps.setString(1, lang);
                var rs = ps.executeQuery();
                if (rs.next())
                {
                    ids.add(rs.getInt("id"));
                } else
                {System.out.println("Sprache nicht gefunden: " + lang);
                    return 0;
                }
                rs.close();
            } catch (SQLException e)
            { throw new RuntimeException(e); }
        }

        // 2. Alte Zuordnungen löschen
        try (var ps = con.prepareStatement( "DELETE FROM vt_filme_sprachen WHERE fk_film = ?"))
        { ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e)
        { throw new RuntimeException(e); }

        // 3. Neue Zuordnungen einfügen
        int inserted = 0;
        for (int langId : ids)
        {
            try (var ps = con.prepareStatement( "INSERT INTO vt_filme_sprachen (fk_film, fk_sprache) VALUES (?, ?)"))
            { ps.setInt(1, id); ps.setInt(2, langId);
                inserted += ps.executeUpdate();
            } catch (SQLException e)
            { throw new RuntimeException(e); }
        }
        return inserted;
    }

    @Override
    public boolean checkFsk(int jahr)
    {
        try(var ps = con.prepareStatement("SELECT 1 FROM t_fsk WHERE Jahr = ?"))
        {
            ps.setInt(1,jahr);
            var rs = ps.executeQuery();
            if(rs.next()){
                return true;

            }else{
                return false;
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }



    public List<String> getGenres(int id)
    {
        List<String> genres = new ArrayList<>();
        String sql = """
                        SELECT t_genres.Genre 
                        FROM vt_filme_genres 
                        JOIN t_genres ON vt_filme_genres.genre_id = t_genres.id 
                        WHERE vt_filme_genres.film_id = ?
                        """;
        try(var ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            while (rs.next())
            {
                genres.add(rs.getString("Genre"));
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return genres;
    }

    @Override
    public List<String> getLanguages(int id)
    {
        List<String> sprachen = new ArrayList<>();
        String sql = """
        SELECT s.sprache
        FROM vt_filme_sprachen fs
        JOIN t_sprachen s ON fs.fk_sprache = s.id
        WHERE fs.fk_film = ?
        """;
        try(var ps = con.prepareStatement(sql))
        {
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            while (rs.next())
            {
                sprachen.add(rs.getString("sprache"));
            }
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return sprachen;
    }


    //TODO add genre & sprachen
    private void buildMovie(PreparedStatement ps, List<Film> filme) throws SQLException
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
            f.setGenres(getGenres(f.getId()));
            f.setSprachen(getLanguages(f.getId()));

            filme.add(f);
        }

    }
}
