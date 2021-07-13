package ru.netology.web.webPage;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.alfabank.alfatest.cucumber.annotations.Name;
import ru.alfabank.alfatest.cucumber.api.AkitaPage;

@Name("Officer")
public class Officer extends AkitaPage {

    @Name("Составитель")
    @FindBy(css = "#officer")
    public SelenideElement officer1;

    @Name("Звание")
    @FindBy(css = "[name=rank]")
    public SelenideElement rank;

    @Name("Должность")
    @FindBy(css = "[name=job_title]")
    public SelenideElement job_title;

    @Name("Код подразделения")
    @FindBy(css = "[name=workplace]")
    public SelenideElement workplace;
}
