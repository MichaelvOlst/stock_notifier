package nl.michaelvanolst.app.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class TaskDto {
  
  private String title;
  private String url;
  private String selector;
  private String contains;
  private int interval;
  private EmailDto email;

  @Override
  public String toString() {
    return this.url + " " + this.contains + " " + this.email.toString() + " " + this.interval + " " + this.selector + " " + this.title;
  }

}
