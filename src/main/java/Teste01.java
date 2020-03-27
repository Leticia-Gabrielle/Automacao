import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;


public class Teste01 {

    //TESTE01 - ACESSAR URL E CADASTRAR USUÁRIO COM SUCESSO

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
        Teste01.nome=nome;
    }
    public static String getEmail() {
        return email;
    }
    public static void setEmail(String email) {
        Teste01.email=email;
    }
    public static String getSenha() {
        return senha;
    }
    public static void setSenha(String senha) {
        Teste01.senha=senha;
    }

    //ACESSA URL
    @Before
    public void acessaUrl(){
        System.getProperty("webdriver.chrome.driver","chromedriver");
        this.driver  = new ChromeDriver();
        wait = new WebDriverWait(driver, 90);
        this.driver.get("https://srbarriga.herokuapp.com/login");
        this.driver.manage().window().maximize();
    }

    //CADASTRA USUÁRIO
    @Test
    public void cadastrarUsuario() throws InterruptedException {

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

    }

    //FECHAR TELA
    @After
    public void fechaBrowser(){
        this.driver.quit();
    }
}

