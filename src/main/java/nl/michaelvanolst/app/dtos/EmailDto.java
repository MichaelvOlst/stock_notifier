package nl.michaelvanolst.app.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
  private String to;
  private String from;
  private String title;

  @Override
  public String toString() {
    return this.to + " " + this.from + " " + this.title;
  }
}
