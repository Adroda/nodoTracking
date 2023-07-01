package tse.java.nodotracking;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackingDTO implements Serializable{

  private static final long serialVersionUID = 1L;
  private Long id;
  
  private String matricula;
  
  private String pais;

  private String longitude;

  private String latitude;

  private String timestamp;
}
