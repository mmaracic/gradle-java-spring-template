package hr.mmaracic.website.api;

import hr.mmaracic.BaseTestConfiguration;
import hr.mmaracic.model.User;
import hr.mmaracic.repository.UserRepository;
import hr.mmaracic.repository.impl.UserCustomRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Not pure MVC test because BaseTestConfiguration will include all beans using component scan
 * Pure MVC test would specify controller bean in @WebMvcTest and mock UserService bean, without @ContextConfiguration
 */
@WebMvcTest
@ContextConfiguration(classes = {BaseTestConfiguration.class})
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserCustomRepositoryImpl userCustomRepositoryImpl;

    @Test
    void testController() {
        assertThat(applicationContext.containsBean("userController"), equalTo(true));
    }

    @Test
    void testUserCount() throws Exception {
        Mockito.when(userRepository.count()).thenReturn(1l);
        MvcResult result = mvc.perform(get("/user/count"))
                .andDo(log()).andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), equalTo("1"));
    }

    @Test
    void testGetUserByName() throws Exception {
        Mockito.when(userRepository.findByName(anyString())).thenReturn(new User("test"));
        MvcResult result = mvc.perform(get("/user").param("name", "user"))
                .andDo(log()).andExpect(status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString(), equalTo("{\"id\":null,\"name\":\"test\",\"created\":null}"));
    }

    @Test
    void testGetUnknownUserByName() throws Exception {
        MvcResult result = mvc.perform(get("/user").param("name", "user"))
                .andDo(log()).andExpect(status().isNotFound())
                .andReturn();
        assertThat(result.getResponse().getErrorMessage(), equalTo("Unknown user"));
    }
}
