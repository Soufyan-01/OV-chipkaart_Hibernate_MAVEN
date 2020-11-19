package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity (name = "product")

public class Product {

    @Id
    @Column(name = "product_nummer")
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;

//    @ManyToMany(
//
//            cascade = CascadeType.ALL
//    )

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    //@Transient
    List<OVChipkaart> ovChipkaarts = new ArrayList<>();

    public Product(){}

    public Product(int product_nummer, String naam, String beschrijving, double prijs){
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public List<OVChipkaart> getOvChipkaarts() {
        return ovChipkaarts;
    }

    public void setOvChipkaarts(List<OVChipkaart> ovChipkaarts) {
        this.ovChipkaarts = ovChipkaarts;
    }

    public void addOVKaart(OVChipkaart ovChipkaart){
        this.ovChipkaarts.add(ovChipkaart);
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public String ownString() {
        String deelEen = "  Product: " + product_nummer + " naam: " + naam + " beschrijving: " + beschrijving + " prijs: " + prijs;
        return deelEen;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (OVChipkaart ovChipkaart : ovChipkaarts)
            stringBuilder.append("\n"+ovChipkaart.ownString());
        return String.format("\n%s%s",
                ownString(),stringBuilder.toString()
        );

    }

}


//        for (OVChipkaart ovChipkaart : ovChipkaarts){s
//            return deelEen + " \n"+ ovChipkaart;
//        }
//        return ovChipkaarts + deelEen;


