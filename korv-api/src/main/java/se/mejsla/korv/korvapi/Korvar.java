package se.mejsla.korv.korvapi;

import java.util.List;

public class Korvar {

  List<Korv> korvar;

  public Korvar(final List<Korv> korvar) {
    this.korvar = List.copyOf(korvar);
  }

  public List<Korv> getKorvar() {
    return korvar;
  }

  @Override
  public String toString() {
    return "Korvar(" +
        "kovar=" + korvar +
        ')';
  }
}
