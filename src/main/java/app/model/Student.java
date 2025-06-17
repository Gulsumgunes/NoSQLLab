package app.model;

import java.io.Serializable;



public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ogrenciNo;
    private String adSoyad;
    private String bolum;

    public Student() {} // Boş constructor (GSON için gerekli)

    public Student(String ogrenciNo, String adSoyad, String bolum) {
        this.ogrenciNo = ogrenciNo;
        this.adSoyad = adSoyad;
        this.bolum = bolum;
    }

    public String getOgrenciNo() { return ogrenciNo; }
    public void setOgrenciNo(String ogrenciNo) { this.ogrenciNo = ogrenciNo; }

    public String getAdSoyad() { return adSoyad; }
    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }

    public String getBolum() { return bolum; }
    public void setBolum(String bolum) { this.bolum = bolum; }
}