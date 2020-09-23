package se.mejsla.korv.korvapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class KorvController {

  private final Korvkylen korvkylen;

  public KorvController(final Korvkylen korvkylen) {
    this.korvkylen = korvkylen;
  }

  @GetMapping("/korvar")
  Korvar getKorv() {
    return new Korvar(korvkylen.korvarna());
  }

  @GetMapping("/korvar/{id}")
  Korv getEnKorv(@PathVariable int id) {
    Optional<Korv> korven = korvkylen.korvarna().stream()
        .filter(korv -> korv.getId() == id)
        .findFirst();

    return korven.orElseThrow(() -> new IngenKorv(id));
  }

  @ExceptionHandler(IngenKorv.class)
  public ResponseEntity<String> ingenKorv(Throwable exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }
}
