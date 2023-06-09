package tse.java.nodotracking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(path = "/api")
public class GetTrackingService {
  Logger logger = LoggerFactory.getLogger(GetTrackingService.class);
  
  @GetMapping(path = "/listaTrackings")
  public @ResponseBody List<TrackingDTO> listarTrackings(@RequestParam String matricula,@RequestParam String pais, @RequestParam String fechaInicio, @RequestParam String fechaFin) throws ClientProtocolException, IOException{
    logger.info("Me invocaron con los parametros matricula: " + matricula + " pais:" + pais + " fechaInicio: " + fechaInicio + " fechaFin: " + fechaFin);
    LocalDateTime fi1 = LocalDateTime.now();
    LocalDateTime ff1 = LocalDateTime.of(2023, 7, 29, 1 ,1 ,1, 1);
    
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime fechaInicioParseada = LocalDateTime.parse(fechaInicio,dateTimeFormatter);
    LocalDateTime fechaFinParseada = LocalDateTime.parse(fechaFin,dateTimeFormatter);
    
    List<String> matriculas = new ArrayList<String>();
    matriculas.add("SDE5687");
    matriculas.add("SDF1254");
    matriculas.add("SDF2112");
    String p1 = "Uruguay";
    List<TrackingDTO> list = new ArrayList<TrackingDTO>();
    System.out.println(fi1.isBefore(fechaInicioParseada));
    System.out.println(fi1);
    System.out.println(fechaInicioParseada);

    if(matriculas.contains(matricula) && p1.equals(pais) && fi1.isBefore(fechaInicioParseada) && ff1.isAfter(fechaFinParseada)){
      logger.info("Los parametros son validos, retorno los trackings registrados");
      CloseableHttpClient hc = HttpClientBuilder.create().build();
    
      LocalDateTime date = LocalDateTime.now();
      String strDate = date.format(dateTimeFormatter);
    
      TrackingDTO t1 = new TrackingDTO(1L, matricula, "Uruguay", "-34.184542", "-55.002278", "2023-07-18 19:54");
      TrackingDTO t2 = new TrackingDTO(2L, matricula, "Uruguay","-33.184542", "-56.002278", "2023-07-17 19:54");
      TrackingDTO t3 = new TrackingDTO(3L, matricula, "Uruguay","-32.184542", "-57.002278", "2023-07-16 19:54");
      
      list.add(t1);
      list.add(t2);
      list.add(t3);
  
      ObjectMapper objectMapper = new ObjectMapper();
      String requestBody = objectMapper.writeValueAsString(list);
      HttpPost request = new HttpPost("http://carga-uy-13.web.elasticloud.uy/CargaUy-web/api/tracking/trackings");
      request.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
      HttpResponse response = hc.execute(request);
    } else {
        logger.info("Los parametros ingresados son INvalidos");
    }
    return list;    
  }
}
