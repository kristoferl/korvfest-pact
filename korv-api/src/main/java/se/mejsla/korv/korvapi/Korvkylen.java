package se.mejsla.korv.korvapi;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class Korvkylen {

  private List<Korv> korvar = Collections.emptyList();

  public Korvkylen() {
    korvar = List.of(
        new Korv(1, "Bullens pilsnerkorv", "odefinierbar", 3),
        new Korv(2, "Falukorv", "menlös", 2),
        new Korv(3, "Fiskkorv", "smakar inte ens kött", -1),
        new Korv(4, "Vegankov", "underbar", 100)
    );
  }

  public List<Korv> korvarna() {
    return List.copyOf(korvar);
  }
}
