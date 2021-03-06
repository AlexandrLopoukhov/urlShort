package urlShortTest;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import urlShort.Application;
import urlShort.service.AccountService;
import urlShort.service.StatisticService;
import urlShort.service.UrlService;
import urlShort.utils.DBInitializator;
import urlShort.utils.RandomStringGenerator;
import urlShort.utils.UrlGenarator;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class GreetingControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testCreateAccount() throws Exception {
        createAccount(1);
    }
    public void createAccount(int accountId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId", accountId);
        this.mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("description").value("Your account is opened"))
                .andExpect(jsonPath("password").isNotEmpty());
    }

    @Test
    public void existingAccountCreation() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId", 2);
        this.mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("description").value("Your account is opened"))
                .andExpect(jsonPath("password").isNotEmpty());
        this.mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("description").value("account with that ID already exists"))
                .andExpect(jsonPath("password").isEmpty());

    }

    @Test
    public void testRegisterUrl() throws Exception {
        registerUrl(1);
    }

    public void registerUrl(int accountId) throws Exception {
        //Register Account
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId", accountId);
        this.mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("description").value("Your account is opened"))
                .andExpect(jsonPath("password").isNotEmpty());
        //get Account token
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        String resultPwd = jdbcTemplate.queryForObject("SELECT PASSWORD FROM ACCOUNT WHERE ID = ?", String.class, accountId);
        //save url
        JSONObject jsonObjectUrl = new JSONObject();
        jsonObjectUrl.put("url", "http://stackoverflow.com/questions/19556039/spring-mvc-controller-rest-service-needs-access-to-header-information-how-to-do");
        jsonObjectUrl.put("redirectType", 301);
        this.mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", resultPwd)
                .content(jsonObjectUrl.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("shortUrl").isNotEmpty());
    }

    @Test
    public void testUrlGenerator() {
        String result = UrlGenarator.generate();
        Assert.assertNotNull(result);
    }

    @Test
    public void getAccountByPassword() throws Exception {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?, ?)", 4, "PWD");
        int accountId = 0;
        accountId = AccountService.getAccountByPassword("PWD");
        Assert.assertEquals(4, accountId);
    }

    @Test
    public void exceptionGetAccountByPassword() throws Exception {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?, ?)", 5, "PWD");
        jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?, ?)", 6, "PWD");
        String exText = null;
        try {
            int accountId = AccountService.getAccountByPassword("PWD");
        } catch (Exception e) {
            exText = e.getMessage();
        }
        Assert.assertEquals("There are more than one account for token", exText);
    }

    @Test
    public void randomStringGenerator(){
        String result = RandomStringGenerator.generate(5);
        Assert.assertEquals(5 ,result.length());
    }

    @Test
    public void getStatistic() throws Exception {
        registerUrl(1);
        String password = AccountService.getAccountPassword(1);
        this.mockMvc.perform(get("/statistic/1").header("Authorization", password))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("statisticMap").isNotEmpty());
    }

    @Test
    public void getStatisticWithRedirect() throws Exception {
        registerUrl(10);
        String password = AccountService.getAccountPassword(10);
        String shortUrl = UrlService.getShortUrlById(10);
        this.mockMvc.perform(get("/r/" + shortUrl));
        this.mockMvc.perform(get("/statistic/10").header("Authorization", password))
                .andDo(print())
                .andExpect(status().isOk())
        .andExpect(jsonPath("statisticMap").isNotEmpty());

    }
    @Test
    public void getDateOfStatisticWithRedirect() throws Exception {
        registerUrl(11);
        String password = AccountService.getAccountPassword(11);
        String shortUrl = UrlService.getShortUrlById(11);

        this.mockMvc.perform(get("/r/" + shortUrl));
        this.mockMvc.perform(get("/r/" + shortUrl));
        Map<String, Integer> map = StatisticService.getStatistic(11);
        for (Map.Entry<String, Integer> m : map.entrySet()) {
            System.out.println(m.getKey());
            System.out.println(m.getValue());
        }
        Assert.assertTrue(!map.isEmpty());

    }
    //DB part
    @Before
    @Test
    public void createDBInstance() throws Exception {
        DBInitializator.getDbInitializator();
    }

    @Test
    public void getDataFromDB() throws Exception {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?, ?)", 7, "PWD");
        String resultPwd = jdbcTemplate.queryForObject("SELECT PASSWORD FROM ACCOUNT WHERE ID = ?", String.class, 7);
        Assert.assertEquals("PWD", resultPwd);
    }

    @Test
    public void checkConstraintException() throws Exception {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        boolean duplicate = false;
        jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?, ?)", 8, "PWD");
        try {
            jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?, ?)", 8, "PWD");
        } catch (DuplicateKeyException e) {
            duplicate = true;
        }
        Assert.assertEquals(true, duplicate);
    }

    @After
    @Test
    public void clearTable() throws Exception {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        jdbcTemplate.update("TRUNCATE TABLE ACCOUNT");
    }

}
