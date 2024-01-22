package org.example.page;

import org.openqa.selenium.WebDriver;

public class AbstractPage implements IPage {

    private WebDriver webDriver;

    public AbstractPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

}