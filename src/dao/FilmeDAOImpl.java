package dao;

import db.DbConnect;

import java.sql.Connection;

public class FilmeDAOImpl implements FilmeDAO
{
    private Connection con = DbConnect.getInstance().connection();
}
