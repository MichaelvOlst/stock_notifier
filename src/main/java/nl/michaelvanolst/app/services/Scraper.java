package nl.michaelvanolst.app.services;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import lombok.AllArgsConstructor;
import nl.michaelvanolst.app.dtos.TaskDto;
import nl.michaelvanolst.app.exceptions.ScraperException;

@AllArgsConstructor
public class Scraper {

  private final TaskDto taskDto;

  public String get() throws ScraperException {

    Logger.info("Started Scraping: " + this.taskDto.getTitle());

    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(
        new BrowserType.LaunchOptions()
        .setHeadless(Config.getBoolean("scraper.headless"))
        .setSlowMo(100)
      );
      Page page = browser.newPage();
      page.navigate(this.taskDto.getUrl());

      String result = "";
      Locator selector = page.locator(this.taskDto.getSelector());
      if(selector.isVisible()) {
        result = selector.textContent();
      }

      page.close();
      browser.close();
      playwright.close();

      Logger.info("Finished Scraping: " + this.taskDto.getTitle());

      return result;

    } catch(Exception ex) {
      throw new ScraperException(ex.getMessage());
    }
  }
}
