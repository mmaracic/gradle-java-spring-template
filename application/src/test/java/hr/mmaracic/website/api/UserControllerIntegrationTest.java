package hr.mmaracic.website.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.mmaracic.model.User;
import hr.mmaracic.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.save(new User("user"));
    }

    @AfterEach
    void cleanup() {
        userRepository.deleteAllInBatch();
    }

    @Test
    void testController() {
        assertThat(applicationContext.containsBean("userController"), equalTo(true));
    }

    @Test
    void testUserCount() throws Exception {
        MvcResult result = mvc.perform(get("/user/count"))
                .andDo(log()).andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), equalTo("1"));
    }

    @Test
    void testGetUserByName() throws Exception {
        MvcResult result = mvc.perform(get("/user").param("name", "user"))
                .andDo(log()).andExpect(status().isOk())
                .andReturn();
        User user = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(user.getName(), equalTo("user"));
        assertThat(user.getId(), notNullValue());
        assertThat(user.getCreated(), notNullValue());
    }

    @Test
    void testGetUnknownUserByName() throws Exception {
        MvcResult result = mvc.perform(get("/user").param("name", "unknown"))
                .andDo(log()).andExpect(status().isNotFound())
                .andReturn();
        assertThat(result.getResponse().getErrorMessage(), equalTo("Unknown user"));
    }
}
