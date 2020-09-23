package se.mejsla.korv.korvcli;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Korvar {

  List<Korv> korvar;

  @JsonCreator
  public Korvar(final List<Korv> korvar) {
    this.korvar = korvar;
  }
}
