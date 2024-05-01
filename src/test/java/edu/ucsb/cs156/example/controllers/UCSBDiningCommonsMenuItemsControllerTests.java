// package edu.ucsb.cs156.example.controllers;

// import edu.ucsb.cs156.example.repositories.UserRepository;
// import edu.ucsb.cs156.example.testconfig.TestConfig;
// import edu.ucsb.cs156.example.ControllerTestCase;
// import edu.ucsb.cs156.example.entities.UCSBDiningCommonsMenuItem;
// import edu.ucsb.cs156.example.repositories.UCSBDiningCommonsMenuItemRepository;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Map;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.context.annotation.Import;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MvcResult;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// @WebMvcTest(controllers = UCSBDiningCommonsMenuItemsController.class)
// @Import(TestConfig.class)
// public class UCSBDiningCommonsMenuItemsControllerTests extends ControllerTestCase {

//         @MockBean
//         UCSBDiningCommonsMenuItemRepository UCSBDiningCommonsMenuItemRepository;

//         @MockBean
//         UserRepository userRepository;

//         // Tests for GET /api/UCSBDiningCommonsMenuItems/all
        
//         @Test
//         public void logged_out_users_cannot_get_all() throws Exception {
//                 mockMvc.perform(get("/api/UCSBDiningCommonsMenuItems/all"))
//                                 .andExpect(status().is(403)); // logged out users can't get all
//         }

//         @WithMockUser(roles = { "USER" })
//         @Test
//         public void logged_in_users_can_get_all() throws Exception {
//                 mockMvc.perform(get("/api/UCSBDiningCommonsMenuItems/all"))
//                                 .andExpect(status().is(200)); // logged
//         }

//         @WithMockUser(roles = { "USER" })
//         @Test
//         public void logged_in_user_can_get_all_UCSBDiningCommonsMenuItems() throws Exception {

//                 // arrange
//                 LocalDateTime ldt1 = LocalDateTime.parse("2022-01-03T00:00:00");

//                 UCSBDiningCommonsMenuItem UCSBDiningCommonsMenuItem1 = UCSBDiningCommonsMenuItem.builder()
//                                 .name("firstDayOfClasses")
//                                 .quarterYYYYQ("20222")
//                                 .localDateTime(ldt1)
//                                 .build();

//                 LocalDateTime ldt2 = LocalDateTime.parse("2022-03-11T00:00:00");

//                 UCSBDiningCommonsMenuItem UCSBDiningCommonsMenuItem2 = UCSBDiningCommonsMenuItem.builder()
//                                 .name("lastDayOfClasses")
//                                 .quarterYYYYQ("20222")
//                                 .localDateTime(ldt2)
//                                 .build();

//                 ArrayList<UCSBDiningCommonsMenuItem> expectedDates = new ArrayList<>();
//                 expectedDates.addAll(Arrays.asList(UCSBDiningCommonsMenuItem1, UCSBDiningCommonsMenuItem2));

//                 when(UCSBDiningCommonsMenuItemRepository.findAll()).thenReturn(expectedDates);

//                 // act
//                 MvcResult response = mockMvc.perform(get("/api/UCSBDiningCommonsMenuItems/all"))
//                                 .andExpect(status().isOk()).andReturn();

//                 // assert

//                 verify(UCSBDiningCommonsMenuItemRepository, times(1)).findAll();
//                 String expectedJson = mapper.writeValueAsString(expectedDates);
//                 String responseString = response.getResponse().getContentAsString();
//                 assertEquals(expectedJson, responseString);
//         }

//         // Tests for POST /api/UCSBDiningCommonsMenuItems/post...

//         @Test
//         public void logged_out_users_cannot_post() throws Exception {
//                 mockMvc.perform(post("/api/UCSBDiningCommonsMenuItems/post"))
//                                 .andExpect(status().is(403));
//         }

//         @WithMockUser(roles = { "USER" })
//         @Test
//         public void logged_in_regular_users_cannot_post() throws Exception {
//                 mockMvc.perform(post("/api/UCSBDiningCommonsMenuItems/post"))
//                                 .andExpect(status().is(403)); // only admins can post
//         }

//         @WithMockUser(roles = { "ADMIN", "USER" })
//         @Test
//         public void an_admin_user_can_post_a_new_UCSBDiningCommonsMenuItem() throws Exception {
//                 // arrange

//                 LocalDateTime ldt1 = LocalDateTime.parse("2022-01-03T00:00:00");

//                 UCSBDiningCommonsMenuItem UCSBDiningCommonsMenuItem1 = UCSBDiningCommonsMenuItem.builder()
//                                 .name("firstDayOfClasses")
//                                 .quarterYYYYQ("20222")
//                                 .localDateTime(ldt1)
//                                 .build();

//                 when(UCSBDiningCommonsMenuItemRepository.save(eq(UCSBDiningCommonsMenuItem1))).thenReturn(UCSBDiningCommonsMenuItem1);

//                 // act
//                 MvcResult response = mockMvc.perform(
//                                 post("/api/UCSBDiningCommonsMenuItems/post?name=firstDayOfClasses&quarterYYYYQ=20222&localDateTime=2022-01-03T00:00:00")
//                                                 .with(csrf()))
//                                 .andExpect(status().isOk()).andReturn();

//                 // assert
//                 verify(UCSBDiningCommonsMenuItemRepository, times(1)).save(UCSBDiningCommonsMenuItem1);
//                 String expectedJson = mapper.writeValueAsString(UCSBDiningCommonsMenuItem1);
//                 String responseString = response.getResponse().getContentAsString();
//                 assertEquals(expectedJson, responseString);
//         }

//         // Tests for GET /api/UCSBDiningCommonsMenuItems?id=...

//         @Test
//         public void logged_out_users_cannot_get_by_id() throws Exception {
//                 mockMvc.perform(get("/api/UCSBDiningCommonsMenuItems?id=7"))
//                                 .andExpect(status().is(403)); // logged out users can't get by id
//         }

//         @WithMockUser(roles = { "USER" })
//         @Test
//         public void test_that_logged_in_user_can_get_by_id_when_the_id_exists() throws Exception {

//                 // arrange
//                 LocalDateTime ldt = LocalDateTime.parse("2022-01-03T00:00:00");

//                 UCSBDiningCommonsMenuItem UCSBDiningCommonsMenuItem = UCSBDiningCommonsMenuItem.builder()
//                                 .name("firstDayOfClasses")
//                                 .quarterYYYYQ("20222")
//                                 .localDateTime(ldt)
//                                 .build();

//                 when(UCSBDiningCommonsMenuItemRepository.findById(eq(7L))).thenReturn(Optional.of(UCSBDiningCommonsMenuItem));

//                 // act
//                 MvcResult response = mockMvc.perform(get("/api/UCSBDiningCommonsMenuItems?id=7"))
//                                 .andExpect(status().isOk()).andReturn();

//                 // assert

//                 verify(UCSBDiningCommonsMenuItemRepository, times(1)).findById(eq(7L));
//                 String expectedJson = mapper.writeValueAsString(UCSBDiningCommonsMenuItem);
//                 String responseString = response.getResponse().getContentAsString();
//                 assertEquals(expectedJson, responseString);
//         }

//         @WithMockUser(roles = { "USER" })
//         @Test
//         public void test_that_logged_in_user_can_get_by_id_when_the_id_does_not_exist() throws Exception {

//                 // arrange

//                 when(UCSBDiningCommonsMenuItemRepository.findById(eq(7L))).thenReturn(Optional.empty());

//                 // act
//                 MvcResult response = mockMvc.perform(get("/api/UCSBDiningCommonsMenuItems?id=7"))
//                                 .andExpect(status().isNotFound()).andReturn();

//                 // assert

//                 verify(UCSBDiningCommonsMenuItemRepository, times(1)).findById(eq(7L));
//                 Map<String, Object> json = responseToJson(response);
//                 assertEquals("EntityNotFoundException", json.get("type"));
//                 assertEquals("UCSBDiningCommonsMenuItem with id 7 not found", json.get("message"));
//         }


//         // Tests for DELETE /api/UCSBDiningCommonsMenuItems?id=... 

//         @WithMockUser(roles = { "ADMIN", "USER" })
//         @Test
//         public void admin_can_delete_a_date() throws Exception {
//                 // arrange

//                 LocalDateTime ldt1 = LocalDateTime.parse("2022-01-03T00:00:00");

//                 UCSBDiningCommonsMenuItem UCSBDiningCommonsMenuItem1 = UCSBDiningCommonsMenuItem.builder()
//                                 .name("firstDayOfClasses")
//                                 .quarterYYYYQ("20222")
//                                 .localDateTime(ldt1)
//                                 .build();

//                 when(UCSBDiningCommonsMenuItemRepository.findById(eq(15L))).thenReturn(Optional.of(UCSBDiningCommonsMenuItem1));

//                 // act
//                 MvcResult response = mockMvc.perform(
//                                 delete("/api/UCSBDiningCommonsMenuItems?id=15")
//                                                 .with(csrf()))
//                                 .andExpect(status().isOk()).andReturn();

//                 // assert
//                 verify(UCSBDiningCommonsMenuItemRepository, times(1)).findById(15L);
//                 verify(UCSBDiningCommonsMenuItemRepository, times(1)).delete(any());

//                 Map<String, Object> json = responseToJson(response);
//                 assertEquals("UCSBDiningCommonsMenuItem with id 15 deleted", json.get("message"));
//         }
        
//         @WithMockUser(roles = { "ADMIN", "USER" })
//         @Test
//         public void admin_tries_to_delete_non_existant_UCSBDiningCommonsMenuItem_and_gets_right_error_message()
//                         throws Exception {
//                 // arrange

//                 when(UCSBDiningCommonsMenuItemRepository.findById(eq(15L))).thenReturn(Optional.empty());

//                 // act
//                 MvcResult response = mockMvc.perform(
//                                 delete("/api/UCSBDiningCommonsMenuItems?id=15")
//                                                 .with(csrf()))
//                                 .andExpect(status().isNotFound()).andReturn();

//                 // assert
//                 verify(UCSBDiningCommonsMenuItemRepository, times(1)).findById(15L);
//                 Map<String, Object> json = responseToJson(response);
//                 assertEquals("UCSBDiningCommonsMenuItem with id 15 not found", json.get("message"));
//         }

//         // Tests for PUT /api/UCSBDiningCommonsMenuItems?id=... 

//         @WithMockUser(roles = { "ADMIN", "USER" })
//         @Test
//         public void admin_can_edit_an_existing_UCSBDiningCommonsMenuItem() throws Exception {
//                 // arrange

//                 LocalDateTime ldt1 = LocalDateTime.parse("2022-01-03T00:00:00");
//                 LocalDateTime ldt2 = LocalDateTime.parse("2023-01-03T00:00:00");

//                 UCSBDiningCommonsMenuItem UCSBDiningCommonsMenuItemOrig = UCSBDiningCommonsMenuItem.builder()
//                                 .name("firstDayOfClasses")
//                                 .quarterYYYYQ("20222")
//                                 .localDateTime(ldt1)
//                                 .build();

//                 UCSBDiningCommonsMenuItem UCSBDiningCommonsMenuItemEdited = UCSBDiningCommonsMenuItem.builder()
//                                 .name("firstDayOfFestivus")
//                                 .quarterYYYYQ("20232")
//                                 .localDateTime(ldt2)
//                                 .build();

//                 String requestBody = mapper.writeValueAsString(UCSBDiningCommonsMenuItemEdited);

//                 when(UCSBDiningCommonsMenuItemRepository.findById(eq(67L))).thenReturn(Optional.of(UCSBDiningCommonsMenuItemOrig));

//                 // act
//                 MvcResult response = mockMvc.perform(
//                                 put("/api/UCSBDiningCommonsMenuItems?id=67")
//                                                 .contentType(MediaType.APPLICATION_JSON)
//                                                 .characterEncoding("utf-8")
//                                                 .content(requestBody)
//                                                 .with(csrf()))
//                                 .andExpect(status().isOk()).andReturn();

//                 // assert
//                 verify(UCSBDiningCommonsMenuItemRepository, times(1)).findById(67L);
//                 verify(UCSBDiningCommonsMenuItemRepository, times(1)).save(UCSBDiningCommonsMenuItemEdited); // should be saved with correct user
//                 String responseString = response.getResponse().getContentAsString();
//                 assertEquals(requestBody, responseString);
//         }

        
//         @WithMockUser(roles = { "ADMIN", "USER" })
//         @Test
//         public void admin_cannot_edit_UCSBDiningCommonsMenuItem_that_does_not_exist() throws Exception {
//                 // arrange

//                 LocalDateTime ldt1 = LocalDateTime.parse("2022-01-03T00:00:00");

//                 UCSBDiningCommonsMenuItem ucsbEditedDate = UCSBDiningCommonsMenuItem.builder()
//                                 .name("firstDayOfClasses")
//                                 .quarterYYYYQ("20222")
//                                 .localDateTime(ldt1)
//                                 .build();

//                 String requestBody = mapper.writeValueAsString(ucsbEditedDate);

//                 when(UCSBDiningCommonsMenuItemRepository.findById(eq(67L))).thenReturn(Optional.empty());

//                 // act
//                 MvcResult response = mockMvc.perform(
//                                 put("/api/UCSBDiningCommonsMenuItems?id=67")
//                                                 .contentType(MediaType.APPLICATION_JSON)
//                                                 .characterEncoding("utf-8")
//                                                 .content(requestBody)
//                                                 .with(csrf()))
//                                 .andExpect(status().isNotFound()).andReturn();

//                 // assert
//                 verify(UCSBDiningCommonsMenuItemRepository, times(1)).findById(67L);
//                 Map<String, Object> json = responseToJson(response);
//                 assertEquals("UCSBDiningCommonsMenuItem with id 67 not found", json.get("message"));

//         }
// }
