package se.mejsla.korv.korvcli;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@EqualsAndHashCode
@ToString
@Getter
public class Korv {

  int id;
  String namn;
  String smak;
  int styrka;
//  String färg;

  @JsonCreator
  public Korv(int id, String namn, String smak, int styrka/*, String färg*/) {
    this.id = id;
    this.namn = namn;
    this.smak = smak;
    this.styrka = styrka;
//    this.färg = färg;
  }
}
