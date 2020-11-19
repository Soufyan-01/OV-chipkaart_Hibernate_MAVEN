package dao;
import domain.Adres;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {

    public Adres findById(int ID);

    public boolean save(Adres adres);

    public boolean update(Adres adres) throws SQLException;

    public boolean delete(Adres adres) throws SQLException;

    public Adres findByReiziger(Reiziger reiziger);

    public List<Adres> findAll();

}
