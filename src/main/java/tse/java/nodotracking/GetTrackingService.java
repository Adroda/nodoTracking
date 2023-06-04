package tse.java.nodotracking;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(path = "/api")
public class GetTrackingService {
  Logger logger = LoggerFactory.getLogger(GetTrackingService.class);
  
  @GetMapping(path = "/listaTrackings")
  public @ResponseBody List<TrackingDTO> listarTrackings(@RequestParam String matricula,@RequestParam String pais, @RequestParam String fechaInicio, @RequestParam String fechaFin) throws ClientProtocolException, IOException{
    String m1 = "ab1234";
    String p1 = "uy";
    LocalDateTime fi1 = LocalDateTime.now();
    LocalDateTime ff1 = LocalDateTime.of(2023, 7, 29, 1 ,1 ,1, 1);
    
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime fechaInicioParseada = LocalDateTime.parse(fechaInicio,dateTimeFormatter);
    LocalDateTime fechaFinParseada = LocalDateTime.parse(fechaFin,dateTimeFormatter);


    //LocalDateTime fechaInicioCasteada = LocalDateTime.of(fechaInicio);
    List<TrackingDTO> list = new ArrayList<TrackingDTO>();

    if(m1.equals(matricula) && p1.equals(pais)  && fi1.isBefore(fechaInicioParseada) && ff1.isAfter(fechaFinParseada)){
      CloseableHttpClient hc = HttpClientBuilder.create().build();
    
      LocalDateTime date = LocalDateTime.now();
      String strDate = date.format(dateTimeFormatter);
    
      TrackingDTO t1 = new TrackingDTO(1L, "ab1234", "uy", "-34.184542", "-55.002278", "2023-05-18 19:54");
      TrackingDTO t2 = new TrackingDTO(2L, "bc1234", "uy","-34.184542", "-55.002278", "2023-05-18 19:54");
      TrackingDTO t3 = new TrackingDTO(3L, "cd1234", "uy","-34.184542", "-55.002278", "2023-05-18 19:54");
      //logger.info("tracking : " + t1.getTimestamp()); ACA ESTAMOS BIEN
      
      list.add(t1);
      list.add(t2);
      list.add(t3);
  
      ObjectMapper objectMapper = new ObjectMapper();
      String requestBody = objectMapper.writeValueAsString(list);
      HttpPost request = new HttpPost("http://localhost:8080/CargaUy-web/api/tracking/trackings");
      request.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
      HttpResponse response = hc.execute(request);
    }
    return list;    
  }
}
