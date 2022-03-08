package nl.michaelvanolst.app.tasks;

import java.util.TimerTask;

import nl.michaelvanolst.app.dtos.TaskDto;
import nl.michaelvanolst.app.services.Logger;
import nl.michaelvanolst.app.services.MailService;
import nl.michaelvanolst.app.services.Scraper;

import javax.mail.MessagingException;
import java.io.IOException;

public class Task extends TimerTask {

  private final TaskDto taskDto;
  private final MailService mailService;

  public Task(TaskDto taskDto, MailService mailService) {
    this.taskDto = taskDto;
    this.mailService = mailService;
  }

  public void run() {
    try {
      Scraper scraper = new Scraper(this.taskDto);
      this.handleResults(scraper.get());
    } catch(Exception ex) {
      Logger.fatal("Error in the scraper: "+ ex.getMessage());
    }
  }

  private void handleResults(String text) throws IOException, MessagingException {

    if(!text.contains(this.taskDto.getContains())) {
      Logger.info("Product not in stock");
      Logger.info("finished");
      return; 
    }

    this.mailService.setTaskDto(this.taskDto);
    this.mailService.setEmailDto(this.taskDto.getEmail());
    this.mailService.send();

    Logger.info("finished");
  }

}
