// app.po.ts
import { browser, element, by } from 'protractor';

export class AppPage {
  navigateTo() {
    // Navigate to the home page of the app
    return browser.get('/');
  }

  getHeadingText() {
    // Get the home page heading element reference
    return element(by.css('app-root Movie Cruiser Application')).getText();
  }
}