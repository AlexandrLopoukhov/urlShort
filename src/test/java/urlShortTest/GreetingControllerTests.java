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
import urlShort.utils.DBInitializator;
import urlShort.utils.UrlGenarator;

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
    public void noParamGreetingShouldReturnDefaultMessage() throws Exception {

        this.mockMvc.perform(get("/greeting"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, World!"));
    }

    @Test
    public void paramGreetingShouldReturnTailoredMessage() throws Exception {

        this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
    }

    @Test
    public void createAccount() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId", 1);
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
    public void registerUrl() throws Exception {
        //Register Account
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountId", 5);
        this.mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("description").value("Your account is opened"))
                .andExpect(jsonPath("password").isNotEmpty());
        //get Account token
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        String resultPwd = jdbcTemplate.queryForObject("SELECT PASSWORD FROM ACCOUNT WHERE ID = ?", String.class, 5);
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
    public void testUrlGenirator(){
        String result = UrlGenarator.generate();
        Assert.assertNotNull(result);
    }

    @Test
    public void getAccountByPassword(){
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?, ?)", 3, "PWD");
        int accountId = AccountService.getAccountByPassword("PWD");
        Assert.assertEquals(3, accountId);


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
        jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?, ?)", 3, "PWD");
        String resultPwd = jdbcTemplate.queryForObject("SELECT PASSWORD FROM ACCOUNT WHERE ID = ?", String.class, 3);
        Assert.assertEquals("PWD", resultPwd);
    }

    @Test
    public void checkConstraintException() throws Exception {
        JdbcTemplate jdbcTemplate = DBInitializator.getJdbcTemplate();
        boolean duplicate = false;
        jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?, ?)", 4, "PWD");
        try {
            jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?, ?)", 4, "PWD");
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
