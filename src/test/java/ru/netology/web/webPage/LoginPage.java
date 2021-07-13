package ru.netology.web.webPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.annotations.Optional;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;


@Name("Страница входа")
public class LoginPage extends AkitaPage {

  @Name("Логин")
  @FindBy(css = "#usr")
  public SelenideElement loginField;

  @Name("Пароль")
  @FindBy(css = "#pass")
  public SelenideElement passwordField;

  @Name("Войти")
  @FindBy(css = "[class=button]")
  public SelenideElement loginButton;

  @Optional
  @FindBy(css = "[data-test-id=error-notification]")
  @Name("неверный логин или пароль")
  public SelenideElement error;

  @Optional
  @FindBy(css = "[data-test-id=error-notification]")
  @Name("пользователь заблокирован")
  public SelenideElement errorUser;
}
