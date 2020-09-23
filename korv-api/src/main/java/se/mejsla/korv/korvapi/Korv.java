package se.mejsla.korv.korvapi;

public class Korv {

  int id;
  String namn;
  String smak;
  int styrka;

  public Korv(final int id, final String namn, final String smak, final int styrka) {
    this.id = id;
    this.namn = namn;
    this.smak = smak;
    this.styrka = styrka;
  }

  public int getId() {
    return id;
  }

  public String getNamn() {
    return namn;
  }

  public String getSmak() {
    return smak;
  }

  public int getStyrka() {
    return styrka;
  }
}
