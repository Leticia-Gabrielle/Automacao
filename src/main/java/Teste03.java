import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;


public class Teste03 {
    //TESTE01 - ACESSAR URL E CRIARCONTA

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
        Teste03.nome = nome;
    }
    public static String getEmail() {
        return email;
    }
    public static void setEmail(String email) {
        Teste03.email = email;
    }
    public static String getSenha() {
        return senha;
    }
    public static void setSenha(String senha) {
        Teste03.senha = senha;
    }

    // ACESSAR URL
    @Before
    public void acessaUrl() {
        System.getProperty("webdriver.chrome.driver", "chromedriver");
        this.driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 90);
        this.driver.get("https://srbarriga.herokuapp.com/login");
        this.driver.manage().window().maximize();
    }

    //CRIA CONTA DO USUÁRIO
    @Test
    public void criarConta() {

        this.random = new Random();
        int number = random.nextInt() * 100;
        setNome("Leticia Gabrielle" + number);
        setEmail("leticia_santos@teste.com" + number);
        setSenha("leticiateste");

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

        //ABAIXO
        //CÓDIGO CRIAR CONTA

        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@class='dropdown-toggle']"))));
        this.driver.findElement(By.xpath("//a[@class='dropdown-toggle']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/addConta']"))));
        this.driver.findElement(By.xpath("//a[@href='/addConta']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.id("nome"))));
        //mudar nome para criar conta
        setNome("Leticia");
        this.driver.findElement(By.id("nome")).sendKeys(getNome());
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//button[@class='btn btn-primary']"))));
        this.driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
        String contacriada = this.driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
        Assert.assertEquals("Conta adicionada com sucesso!",contacriada);

    }

    //FECHA TELA
    @After
    public void fechaBrowser() {
        this.driver.quit();
    }
}

