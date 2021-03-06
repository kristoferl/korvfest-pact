package se.mejsla.korv.korvcli;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(PactConsumerTestExt.class)
class KorvklientConsumerTest {

  static Korvklient korvklient;

  @BeforeAll
  static void beforeAll() {
    korvklient = new Korvklient();
  }

  @Pact(provider = "korv-api", consumer = "korv-cli")
  RequestResponsePact fetchSausages(PactDslWithProvider builder) {

    DslPart body = newJsonBody(root -> {
      root.minArrayLike("korvar", 1, korv -> {
        korv.numberType("id", 42);
        korv.stringType("namn", "Chorizo");
//        korv.stringType("färg", "ljuvligt röd");
      });
    }).build();

    return builder
        .given("Det finns korvar")
        .uponReceiving("en förfrågan på alla korvar")
        .path("/korvar")
        .method("GET")
        .willRespondWith()
        .status(HttpStatus.OK.value())
        .body(body)
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "fetchSausages", port = "8080")
  void verifieraAllKorvar() {
    Korvar korvar = korvklient.hämtaAlla();
    assertThat(korvar.getKorvar()).hasSize(1);
//    final Korv förväntadKorv = new Korv(42, "Chorizo", null, 0, "ljuvligt röd");
    final Korv förväntadKorv = new Korv(42, "Chorizo", null, 0);
    assertThat(korvar.getKorvar().get(0)).isEqualTo(förväntadKorv);
  }

  @Pact(provider = "korv-api", consumer = "korv-cli")
  RequestResponsePact fetchSausage(PactDslWithProvider builder) {

    DslPart body = newJsonBody(root -> {
      root.numberType("id", 42);
      root.stringType("namn", "Chorizo");
    }).build();

    return builder
        .given("Det finns en korv med id 42")
        .uponReceiving("en förfrågan på en korv 42")
        .path("/korvar/42")
        .method("GET")
        .willRespondWith()
        .status(HttpStatus.OK.value())
        .body(body)
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "fetchSausage", port = "8080")
  void verifieraHämtningAvEnKorv() {
    Korv korv = korvklient.hämta(42);
    final Korv förväntadKorv = new Korv(42, "Chorizo", null, 0);
    assertThat(korv).isEqualTo(förväntadKorv);
  }

  @Pact(provider = "korv-api", consumer = "korv-cli")
  RequestResponsePact fetchNonExistingSausage(PactDslWithProvider builder) {

    return builder
        .given("Det finns inga korvar")
        .uponReceiving("en förfrågan på en korv")
        .path("/korvar/42")
        .method("GET")
        .willRespondWith()
        .status(HttpStatus.NOT_FOUND.value())
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "fetchNonExistingSausage", port = "8080")
  void verifieraHämtningsförsökAvIckeExisterandeKorv() {
    assertThatThrownBy(() -> korvklient.hämta(42)).isInstanceOf(NotFound.class);
  }

  @Pact(provider = "korv-api", consumer = "korv-cli")
  RequestResponsePact fetchNonExistingSausages(PactDslWithProvider builder) {

    DslPart body = newJsonBody(root -> {
      root.minArrayLike("korvar", 0, korv -> {
      });
    }).build();

    return builder
        .given("Det finns inga korvar")
        .uponReceiving("en förfrågan på alla korvar")
        .path("/korvar")
        .method("GET")
        .willRespondWith()
        .status(HttpStatus.OK.value())
        .body(body)
        .toPact();
  }

  @Test
  @PactTestFor(pactMethod = "fetchNonExistingSausages", port = "8080")
  void verifieraHämtningsförsökAvIckeExisterandeKorvar() {
    Korvar korvar = korvklient.hämtaAlla();
    assertThat(korvar.getKorvar()).hasSize(0);
  }
}