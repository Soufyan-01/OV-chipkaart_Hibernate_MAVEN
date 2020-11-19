package domain;


import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "ov_chipkaart")
public class OVChipkaart {

    @Id
    @Column(name = "kaart_nummer")
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private double saldo;
//
//    @ManyToOne
//   // @JoinColumn(name = "reiziger_id")
    @ManyToOne
    @JoinColumn( name = "reiziger_id")

    //@Transient
    private Reiziger reiziger;

//    @ManyToMany

    @ManyToMany
            @JoinTable(
                    name = "ov_chipkaart_product", joinColumns = @JoinColumn(name = "kaart_nummer"), inverseJoinColumns = @JoinColumn(name = "product_nummer")
            )
    //@Transient
    List<Product> products = new ArrayList<>();

    public OVChipkaart(){}

    public OVChipkaart(
            int kaart_nummer, Date geldig_tot, int klasse, double saldo, Reiziger reiziger
    ){
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }
    public OVChipkaart(
            int kaart_nummer, Date geldig_tot, int klasse, double saldo
    ){
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
    }


    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public OVChipkaart(int kaart_nummer){
        this.kaart_nummer = kaart_nummer;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }

    public String ownString(){
        String ov = "   kaartnummer: " + kaart_nummer + " geldig_tot: " + geldig_tot + " saldo: " + saldo + " klasse: " +klasse
                + " eigendom van: "+reiziger.reizString() ;
        return ov;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : products)
            stringBuilder.append("\n"+product.ownString());
        return String.format("\n%s%s",
                ownString(), stringBuilder.toString()
        );
    }

}
