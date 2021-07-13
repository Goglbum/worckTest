package ru.netology.web.step;

import com.codeborne.selenide.SelenideElement;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Когда;
import lombok.val;
import ru.alfabank.alfatest.cucumber.api.AkitaScenario;
import ru.alfabank.steps.BaseMethods;
import ru.netology.web.data.DataHelper;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.SQL.SQLQuery.getCode;
import static ru.netology.web.data.DataHelper.*;
import static ru.netology.web.rest.TestRestQuery.*;


public class TemplateStepsTest {
  private final AkitaScenario scenario = AkitaScenario.getInstance();
  private DataHelper.DataHelperRepository repository = DataHelper.getRepository();

  @Когда("^пользователь \"([^\"]*)\" вводит код верификации$")
  public void setVerificationCode(String user) throws SQLException {
    BaseMethods baseMethods = new BaseMethods();
    String code = baseMethods.getPropertyOrStringVariableOrValue(getCode(user));
    SelenideElement valueInput = scenario.getCurrentPage().getElement("Код");
    baseMethods.cleanField("Код");
    valueInput.setValue(code);
  }


  @Когда("^пользователь \"([^\"]*)\" вводит пароль \"([^\"]*)\" и код верификации$")
  public void userLogin(String user, String pass) throws SQLException {
    loginUser(user, pass);
    repository = setToken(verificationUser(user));
  }

  @Когда("^пользователь переводит \"([^\"]*)\"р. с карты \"([^\"]*)\" на карту \"([^\"]*)\"$")
  public void transferManyCardToCard(String amount, String fromCard, String toCard) {
    val authInfo = new DataHelper.AuthInfo(amount);
    repository = setStartingBalance(repository, authInfo,
            getCardBalance(repository.getToken(), authInfo.getCard1().substring(15)),
            getCardBalance(repository.getToken(), authInfo.getCard2().substring(15)));
    fromCard = initFromCard(authInfo, fromCard);
    toCard = initToCard(authInfo, toCard);
    transferMany(repository.getToken(), amount, fromCard, toCard);
    repository = setResultBalance(repository,
            getCardBalance(repository.getToken(), authInfo.getCard1().substring(15)),
            getCardBalance(repository.getToken(), authInfo.getCard2().substring(15)));
  }

  @И("^пользователь проверяет баланс карт$")
  public void chekBalanceCard() {
    assertEquals(repository, calculationBalance(repository));
  }
}


