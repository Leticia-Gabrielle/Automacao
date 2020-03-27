import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Random;

public class SiteBatistaAutomacao {
    //TESTE SITE DE AUTOMACAO DO BATISTA

    private ChromeDriver driver;
    private WebDriverWait wait;
    private Alert alert;
    private static String nome = "";
    private Random random;

    //ACESSA URL
    @Before
    public void acessaUrl(){
        System.getProperty("webdriver.chrome.driver","chromedriver");
        this.driver  = new ChromeDriver();
        wait = new WebDriverWait(driver, 90);
        this.driver.get("https://automacaocombatista.herokuapp.com/treinamento/home");
        this.driver.manage().window().fullscreen();
    }

    //PREENCHE OS CAMPOS E REALIZA O CADASTRO
    @Test
    public void cadastroComSucesso(){
        this.driver.findElement(By.xpath("//a[@class='collapsible-header ']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/users/new']"))));
        this.driver.findElement(By.xpath("//a[@href='/users/new']")).click();
    //criar novo nome
        this.random = new Random();
        int number = random.nextInt() * 1000;
        setNome("LETICIA" + number);
        this.driver.findElement(By.id("user_name")).sendKeys(getNome());
        this.driver.findElement(By.id("user_lastname")).sendKeys("GABRIELLE");
        this.driver.findElement(By.id("user_email")).sendKeys("leticia@teste.com.br");
        this.driver.findElement(By.xpath("//input[@name='commit']")).click();
        this.wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("notice"))));
        //valida usuario criado com sucesso
        String valorTela = this.driver.findElement(By.id("notice")).getText();
        Assert.assertEquals("Usuário Criado com sucesso", valorTela);
    }

    //EXCLUI O USUARIO QUE FOI CRIADO
    @Test
    public void excluirUsuario() throws InterruptedException {
        this.driver.findElement(By.xpath("//a[@class='collapsible-header ']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/users']"))));
        this.driver.findElement(By.xpath("//a[@href='/users']")).click();
        this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement((By.xpath("/html/body/div[3]/div/table/tbody/tr[1]/td[11]/a")))));
        while(this.driver.findElements(By.xpath("//a[contains(text(),'delete') and parent::td/parent::tr/child::td[contains(text(),'"+ getNome()+"')]]")).size() == 0){
            Thread.sleep(1000);
            this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[contains(text(),'Próximo')]"))));
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.scrollBy(0,100)", "");
            jse.executeScript("arguments[0].click();", this.driver.findElement(By.xpath("//a[contains(text(),'Próximo')]")));
        }
        this.driver.findElement(By.xpath("//a[contains(text(),'delete') and parent::td/parent::tr/child::td[contains(text(),'"+ getNome()+"')]]")).click();
        alert = driver.switchTo().alert();
        alert.accept();
        this.wait.until(ExpectedConditions.visibilityOf(this.driver.findElement(By.id("notice"))));
        //VALIDA EXCLUSAO DO USUARIO
        String texto_confirma = this.driver.findElement(By.id("notice")).getText();
        Assert.assertEquals("Seu Usuário foi removido com sucesso!", texto_confirma);
    }

    //SELECIONAR OS DADOS (SELECT)
    @Test
    public void selecionarItensLista() throws InterruptedException {

            this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@class='collapsible-header waves-teal' and contains(text(),'Busca de elementos')]"))));
            this.driver.findElement(By.xpath("//a[@class='collapsible-header waves-teal' and contains(text(),'Busca de elementos')]")).click();
            this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//a[@href='/buscaelementos/dropdowneselect']"))));
            this.driver.findElement(By.xpath("//a[@href='/buscaelementos/dropdowneselect']")).click();
            this.wait.until(ExpectedConditions.elementToBeClickable(this.driver.findElement(By.xpath("//div[@class='select-wrapper' and following-sibling::label[contains(text(),'Desenho Favorito')]]"))));
            this.driver.findElement(By.xpath("//div[@class='select-wrapper' and following-sibling::label[contains(text(),'Desenho Favorito')]]")).click();
            Thread.sleep(3000);
            this.driver.findElement(By.xpath("//span[contains(text(),'Dragon Ball')]")).click();
            Select select = new Select(this.driver.findElement(By.xpath("//select[@id='dropdown']")));
            select.selectByVisibleText("Firefox");
            Thread.sleep(3000);

    }
    ///FECHAR TELA
    @After
    public void fechaBrowser(){
        this.driver.quit();
    }

    //GET E SET
    public static String getNome() {
        return nome;
    }
    public static void setNome(String nome) {
        SiteBatistaAutomacao.nome = nome;
    }
}
