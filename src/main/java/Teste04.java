import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class Teste04 {
    //TESTE04 - ACESSA URL E REALIZA A MOVIMENTACAO DA CONTA COM SUCESSO

    private ChromeDriver driver;
    private WebDriverWait wait;
    private static String nome = "";
    private static String email = "";
    private static String senha = "";
    private Random random;

    public static String getNome() {
        return nome;
    }
    public static void setNome(String nome) {
        Teste04.nome = nome;
    }
    public static String getEmail() {
        return email;
    }
    public static void setEmail(String email) {
        Teste04.email = email;
    }
    public static String getSenha() {
        return senha;
    }
    public static void setSenha(String senha) {
        Teste04.senha = senha;
    }

    //ACESSA URL
    @Before
    public void acessaUrl() {
        System.getProperty("webdriver.chrome.driver", "chromedriver");
        this.driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 90);
        this.driver.get("https://srbarriga.herokuapp.com/login");
        this.driver.manage().window().maximize();
    }


    @Test
    public void movimentacao() {

        this.random = new Random();
        int number = random.nextInt() * 100;
        setNome("Leticia Gabrielle" + number);
        setEmail("leticia_santos@teste.com" + number);
        setSenha("leticiateste");

        //cadastro
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/cadastro']"))));
        this.driver.findElement(By.xpath("//a[@href='/cadastro']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("nome"))));
        this.driver.findElement(By.id("nome")).sendKeys(getNome());
        this.driver.findElement(By.id("email")).sendKeys(getEmail());
        this.driver.findElement(By.id("senha")).sendKeys(getSenha());
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//input[@class='btn btn-primary']"))));
        this.driver.findElement(By.xpath("//input[@class='btn btn-primary']")).click();

        //vê se o texto está correto e valida a tentativa de cadastro
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//div[@class='alert alert-success']"))));
        String texto_confirma = this.driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Usuário inserido com sucesso", texto_confirma);

        //PREENCHE OS DADOS DO LOGIN
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("email"))));
        this.driver.findElement(By.id("email")).sendKeys(getEmail());
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("senha"))));
        this.driver.findElement(By.id("senha")).sendKeys(getSenha());
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//button[@class='btn btn-primary']"))));
        this.driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//div[@class='alert alert-success']"))));
        String texto_valida = this.driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Bem vindo, " + getNome() + "!", texto_valida);

        //cria conta
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@class='dropdown-toggle']"))));
        this.driver.findElement(By.xpath("//a[@class='dropdown-toggle']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/addConta']"))));
        this.driver.findElement(By.xpath("//a[@href='/addConta']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("nome"))));
        setNome(getNome());
        this.driver.findElement(By.id("nome")).sendKeys(getNome());
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//button[@class='btn btn-primary']"))));
        this.driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
        String contacriada = this.driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta adicionada com sucesso!",contacriada);

        //PREENCHE OS CAMPOS DA TELA DE MOVIMENTACAO
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/movimentacao']"))));
        this.driver.findElement(By.xpath("//a[@href='/movimentacao']")).click();
        Select select = new Select(this.driver.findElement(By.xpath("//select[@id='tipo']")));
        select.selectByVisibleText("Despesa");
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("data_transacao"))));
        this.driver.findElement(By.id("data_transacao")).sendKeys("06/03/2020");
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("data_pagamento"))));
        this.driver.findElement(By.id("data_pagamento")).sendKeys("18/01/2020");
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("descricao"))));
        this.driver.findElement(By.id("descricao")).sendKeys("ALUGUEL");
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("interessado"))));
        this.driver.findElement(By.id("interessado")).sendKeys("LETICIA GABRIELLE");
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("valor"))));
        this.driver.findElement(By.id("valor")).sendKeys("500");
        Select selectConta = new Select(this.driver.findElement(By.xpath("//select[@id='conta']")));
        // O nome tem que ser o mesmo do teste03, onde eu crio a conta
        selectConta.selectByVisibleText(getNome());
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("status_pago"))));
        this.driver.findElement(By.id("status_pago")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//button[@class='btn btn-primary']"))));
        this.driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();

        //VALIDA A MOVIMENTACAO COM SUCESSO
        String movimentacaoComSucesso = this.driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Movimentação adicionada com sucesso!", movimentacaoComSucesso);
    }

    //FECHA TELA
    @After
    public void fechaBrowser() {
        this.driver.quit();
    }
}
