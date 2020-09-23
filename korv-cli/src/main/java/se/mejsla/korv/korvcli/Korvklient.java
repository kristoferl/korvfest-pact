package se.mejsla.korv.korvcli;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.util.Arrays;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Korvklient {

  RestTemplate restTemplate;

  public Korvklient() {
    restTemplate = createRestTemplate();
  }

  public Korvar hämtaAlla() {
    ResponseEntity<Korvar> forEntity = restTemplate.getForEntity("http://localhost:8080/korvar", Korvar.class);
    return forEntity.getBody();
  }

  public Korv hämta(final int id) {
    ResponseEntity<Korv> forEntity = restTemplate.getForEntity("http://localhost:8080/korvar/"+ id, Korv.class);
    return forEntity.getBody();
  }

  private RestTemplate createRestTemplate() {
    RestTemplate template = new RestTemplate();
    template.setMessageConverters(
        Arrays.asList(new MappingJackson2HttpMessageConverter(getObjectMapper()), new FormHttpMessageConverter()));
    return template;
  }

  private static ObjectMapper getObjectMapper() {

    return new ObjectMapper()
        .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .setSerializationInclusion(Include.NON_NULL)
        .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, false)
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule())
        .registerModule(new ParameterNamesModule())
        .registerModule(new JsonComponentModule());
  }

}
