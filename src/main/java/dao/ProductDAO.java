package dao;

import domain.OVChipkaart;
import domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    public boolean save(Product product);

    public boolean update(Product product) throws SQLException;

    public boolean delete(Product product) throws SQLException;

    Product findById(int id);

    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);

    public List<Product> findAll();

}
