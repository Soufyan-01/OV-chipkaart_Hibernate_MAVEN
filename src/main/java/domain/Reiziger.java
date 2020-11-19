package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Reiziger")

public class Reiziger implements Serializable {

    @Id
    @Column(name = "reiziger_id")
    private int ID;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private java.sql.Date geboortedatum;

    @OneToOne(
mappedBy = "reiziger", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)

 //   @Transient
    private Adres adres;


    @OneToMany(mappedBy = "reiziger" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)

   // @Transient
    private List<OVChipkaart> ovChipkaarts = new ArrayList<>();

    public Reiziger(){}

    public Reiziger(int ID, String voorletters, String tussenvoegsel,
                    String achternaam, java.sql.Date geboortededatum) {
        this.ID = ID;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortededatum;
    }

    public Adres getAdres(){
        return adres;
    }

    public  int getID(){
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNaam(){
        return voorletters + achternaam;
    }

    public String reizString(){
        String resultaat = (" " + voorletters
                + ". " + tussenvoegsel + " " + achternaam);
        if(tussenvoegsel == null){
            resultaat = (" " + voorletters
                    + " " + achternaam);
        }
        return resultaat;
    }

    public String toString(){
        String resultaat = ("Reiziger ID: " + ID + " voorletters: " + voorletters
                + " tussenvoegsels: " + tussenvoegsel + " achternaam: " + achternaam
                + " geboortedatum: " + geboortedatum);
        if(tussenvoegsel == null){
            resultaat = ("Reiziger ID: " + ID + " voorletters: " + voorletters
                    + " achternaam: " + achternaam
                    + " geboortedatum: " + geboortedatum);
        }
        if (adres == null) {
            resultaat += " adres niet gevonden ";
        }else{
            resultaat+= " het adres is: " + adres.toString();
        }
        return resultaat;
    }


    public String ownString() {
        String resultaat = ("Reiziger ID: " + ID + " voorletters: " + voorletters
                + " tussenvoegsels: " + tussenvoegsel + " achternaam: " + achternaam
                + " geboortedatum: " + geboortedatum);
        return resultaat;
    }

    // vanaf hier begin ik met de statement update
    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(java.sql.Date geboortededatum) {
        this.geboortedatum = geboortededatum;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOvChipkaarts() {
        return ovChipkaarts;
    }

    public void setOvChipkaarts(List<OVChipkaart> ovChipkaarts) {
        this.ovChipkaarts = ovChipkaarts;
    }
}
