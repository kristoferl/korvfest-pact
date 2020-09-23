package se.mejsla.korv.korvapi;

public class IngenKorv extends RuntimeException {


  public IngenKorv(final int id) {
    super("Ingen korv finns med id=" + id + ", 😞");
  }
}
