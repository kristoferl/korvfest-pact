package se.mejsla.korv.korvapi;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.VerificationReports;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Provider("korv-api")
@VerificationReports
@PactFolder("../korv-cli/target/pacts")
//@PactBroker(host = "localhost", port="9292")
public class KorvApiContractVerificationTest {

  Korv bullens = new Korv(1, "Bullens pilsnerkorv", "odefinierbar", 3);
  Korv falu = new Korv(2, "Falukorv", "menlös", 2);
  Korv vege = new Korv(42, "Vegankov", "underbar", 100);

  @MockBean
  Korvkylen korvkylen;

  @LocalServerPort
  private int serverPort;

  @BeforeEach
  void setUp(PactVerificationContext context) {
    context.setTarget(new HttpTestTarget("localhost", serverPort, "/"));
  }

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  void verifyContract(PactVerificationContext context) {
    context.verifyInteraction();
  }

  @State("Det finns korvar")
  public void sausagesExists() {
    when(korvkylen.korvarna()).thenReturn(List.of(bullens, falu));
  }

  @State("Det finns en korv med id 42")
  public void sausages42Exists() {
    when(korvkylen.korvarna()).thenReturn(List.of(vege));
  }

  @State("Det finns inga korvar")
  public void noSausagesExists() {
    when(korvkylen.korvarna()).thenReturn(Collections.emptyList());
  }
}
