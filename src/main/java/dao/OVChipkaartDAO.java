package dao;

import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {

    public OVChipkaart findByKaartNummer(int kaart_nummer) throws SQLException;

    public boolean save (OVChipkaart ovChipkaart);

    public boolean update(OVChipkaart ovChipkaart) throws SQLException;

    public boolean delete(OVChipkaart ovChipkaart) throws SQLException;

    public List<OVChipkaart> findByReiziger(Reiziger reiziger);

    public List<OVChipkaart> findAll();


}
