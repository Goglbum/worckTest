package ru.netology.web.webPage.protocols;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;
import ru.netology.web.webPage.Officer;


@Name("app")
public class App extends AkitaPage {

  Officer officer = new Officer();

  @Name("Место составления")
  @FindBy(css = "#creation_place")
  public SelenideElement creation_place;

  @Name("Место нарушения")
  @FindBy(css = "#violation_place")
  public SelenideElement violation_place;

  @Name("Удостоверяющие документы")
  @FindBy(css = "#idocviolator1")
  public SelenideElement idocviolator1;

  @Name("ерия номер документа")
  @FindBy(css = "#docsviolator1")
  public SelenideElement docsviolator1;

  @Name("кнопка автозаполнения по номеру документа")
  @FindBy(css = "#violator1")
  public SelenideElement violator1;
}

//  LoginPage loginPage = new LoginPage();
//
//  public LoginPage getLoginPage() {
//    return loginPage;
//  }
  //  @Optional
//  @FindBy(css = "[data-test-id=error-notification]")
//  @Name("неверный логин или пароль")
//  public SelenideElement error;
//
//  @Optional
//  @FindBy(css = "[data-test-id=error-notification]")
//  @Name("пользователь заблокирован")
//  public SelenideElement errorUser;
//}
