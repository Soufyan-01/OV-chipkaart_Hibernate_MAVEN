package domain;

import javax.persistence.*;

@Entity (name = "Adres")

public class Adres {

    @Id
    @Column(name = "adres_id")
    private int ID;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    @OneToOne(
            cascade = CascadeType.ALL, fetch = FetchType.LAZY

    )
    @JoinColumn(name = "reiziger_id")
   // @Transient
    private Reiziger reiziger;

    public Adres(){}

    public Adres(int ID, String postcode, String huisnummer,
                 String straat, String woonplaats, Reiziger reiziger){
        this.ID = ID;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger = reiziger;
    }

    public Adres(int ID, String postcode, String huisnummer, String straat, String woonplaats) {
        this.ID = ID;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String toString(){
        String resultaat =  ("Adres{"+" adres_id: "+ ID + " huisnummer: " + huisnummer + " postcode: "+ postcode + " straat: "
                + straat +  " woonplaats: "+ woonplaats + "}");
        if (reiziger == null) {
            resultaat += " reiziger niet gevonden ";
        }else{
            resultaat+= ", De bijbehorende reiziger is: " + reiziger.ownString();
        }
        return resultaat;
    }
}
