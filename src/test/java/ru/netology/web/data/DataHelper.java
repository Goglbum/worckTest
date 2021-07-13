package ru.netology.web.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Random;

public class DataHelper {
  private DataHelper() {

  }

  @AllArgsConstructor
  @Value
  @Getter
  public static class AuthInfo {
    private String amount;
    private String card1;
    private String card2;


    public AuthInfo(String login, String password) {
      this.amount = randomAmount();
      this.card1 = "5559 0000 0000 0001";
      this.card2 = "5559 0000 0000 0002";
    }

    public AuthInfo(String amount) {
      this.amount = amount;
      this.card1 = "5559 0000 0000 0001";
      this.card2 = "5559 0000 0000 0002";
    }

    public AuthInfo(int min, int max) {
      this.amount = randomAmount(min, max);
      this.card1 = "5559 0000 0000 0001";
      this.card2 = "5559 0000 0000 0002";
    }
  }

    public static String randomAmount(int min, int max) {
      Random random = new Random();
      int dif = max - min;
      int randomAmount = random.nextInt(dif + 1);
      randomAmount += min;
      return String.valueOf(randomAmount);
    }

    public static String randomAmount() {
      return randomAmount(0, 10000);
    }


  @Value
  public static class DataHelperRepository {
    private String startingBalanceCard1;
    private String startingBalanceCard2;
    private String resultBalanceCard1;
    private String resultBalanceCard2;
    private String transferAmount;
    private final String token;

  }

  public static DataHelperRepository getRepository(){
    return new  DataHelperRepository(null, null, null, null, null, null);
  }

  public static DataHelperRepository setToken(String token) {
    return new DataHelperRepository(null, null, null, null, null, token);
  }

  public static DataHelperRepository setStartingBalance(DataHelperRepository repository, AuthInfo authInfo, String balanceCard1, String balanceCard2) {
    return new DataHelperRepository(balanceCard1, balanceCard2,
            null, null, authInfo.getAmount(), repository.token);
  }

  public static DataHelperRepository setResultBalance(DataHelperRepository repository, String balanceCard1, String balanceCard2) {
    return new DataHelperRepository(repository.startingBalanceCard1, repository.startingBalanceCard2,
            balanceCard1, balanceCard2, repository.transferAmount, repository.token);
  }

  @Value
  public static class VerificationCode {
    private String code;
  }

  public static VerificationCode getVerificationCodeFor() {
    return new VerificationCode("12345");
  }



  @Value
  private static class Calculate {
    private int fromCard1;
    private int fromCard2;
    private int toCard1;
    private int toCard2;
  }
  private static Calculate getCalculate() {
    return new Calculate(0, 0, 0, 0);
  }

  private static Calculate setFromCard(Calculate calculate, int fromCard1, int fromCard2){
    return new Calculate(fromCard1, fromCard2, calculate.getToCard1(), calculate.getToCard2());
  }

  private static Calculate setToCard(Calculate calculate, int toCard1, int toCard2) {
    return new Calculate(calculate.getFromCard1(), calculate.getFromCard2(), toCard1, toCard2);
  }

  private static Calculate calculate = getCalculate();
  public static String initFromCard(AuthInfo authInfo, String card) {
    if (card.equalsIgnoreCase("1")) {
      calculate = setFromCard(calculate, 1, 0);
      return authInfo.getCard1();
    }
    if (card.equalsIgnoreCase("2")) {
      calculate = setFromCard(calculate, 0, 1);
      return authInfo.getCard2();
    }
    return card;
  }
  public static String initToCard(AuthInfo authInfo, String card) {
    if (card.equalsIgnoreCase("1")) {
      calculate = setToCard(calculate, 1, 0);
      return authInfo.getCard1();
    }
    if (card.equalsIgnoreCase("2")) {
      calculate = setToCard(calculate, 0, 1);
      return authInfo.getCard2();
    }
    return card;
  }

  public static String calculationPlusBalance(String startingBalance, String amount) {
    int result = Integer.parseInt(startingBalance) + Integer.parseInt(amount);
    return String.valueOf(result);
  }

  public static String calculationMinusBalance(String startingBalance, String amount) {
    int result = Integer.parseInt(startingBalance) - Integer.parseInt(amount);
    return String.valueOf(result);
  }

  public static DataHelperRepository calculationBalance(DataHelperRepository repository) {
    if (calculate.getFromCard1() == 1 && calculate.getToCard2() == 1) {
      return setResultBalance(repository,
              calculationMinusBalance(repository.getStartingBalanceCard1(), repository.getTransferAmount()),
              calculationPlusBalance(repository.getStartingBalanceCard2(), repository.getTransferAmount()));
    }
    if (calculate.getFromCard2() == 1 && calculate.getToCard1() == 1) {
      return setResultBalance(repository,
              calculationPlusBalance(repository.getStartingBalanceCard1(), repository.getTransferAmount()),
              calculationMinusBalance(repository.getStartingBalanceCard2(), repository.getTransferAmount()));
    }
    if (calculate.getFromCard1() == 1 && calculate.getToCard2() == 0) {
      return setResultBalance(repository,
              calculationMinusBalance(repository.getStartingBalanceCard1(), repository.getTransferAmount()),
              repository.getStartingBalanceCard2());
    }
    if (calculate.getFromCard2() == 1 && calculate.getToCard1() == 0) {
      return setResultBalance(repository,
              repository.getStartingBalanceCard1(),
              calculationMinusBalance(repository.getStartingBalanceCard2(), repository.getTransferAmount()));
    }
    if (calculate.getFromCard1() == 0 && calculate.getToCard2() == 1) {
      return setResultBalance(repository,
              repository.getStartingBalanceCard1(),
              calculationPlusBalance(repository.getStartingBalanceCard2(), repository.getTransferAmount()));
    }
    if (calculate.getFromCard2() == 0 && calculate.getToCard1() == 1) {
      return setResultBalance(repository,
              calculationPlusBalance(repository.getStartingBalanceCard1(), repository.getTransferAmount()),
              repository.getStartingBalanceCard2());
    }
    return null;
  }
}
