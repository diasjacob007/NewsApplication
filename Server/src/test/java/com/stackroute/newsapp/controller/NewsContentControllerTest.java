package com.stackroute.newsapp.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.newsapp.controller.NewsContentController;
import com.stackroute.newsapp.domain.NewsData;
import com.stackroute.newsapp.exceptions.NewsContentNotFoundException;
import com.stackroute.newsapp.service.NewsContentServiceIntrf;

@RunWith(SpringRunner.class)
@WebMvcTest(NewsContentController.class)
public class NewsContentControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private NewsContentServiceIntrf service;

	private NewsData newsContent;

	static List<NewsData> newsContentLst;

	@Before
	public void setUp() {
		newsContentLst = new ArrayList<>();
		newsContent = new NewsData(1, "Lorie Konish",
				"This workplace perk could make it easier to save for emergencies - CNBC",
				"If you're like most Americans, you're woefully unprepared for costly emergencies. Some retirement plan providers are working on new ways to make it easier for you to save.",
				"https://www.cnbc.com/2019/01/25/this-workplace-benefit-could-make-it-easier-to-save-for-emergencies.html",
				"https://fm.cnbc.com/applications/cnbc.com/resources/img/editorial/2016/05/16/103639738-GettyImages-109686576.1910x1000.jpg",
				"2019-01-26T14:00:08Z",
				"It is still too early to see the full results of the new option, as no participating employees have taken withdrawals yet, according to Haas-Winkelman. The emergency savings tool is available to all workers on the MGM Studios payroll",
				"amal123","Admin");
		newsContentLst.add(newsContent);
	}

	@Test
	public void testSaveNewNewsContent() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbWFsMTIzOkFkbWluIiwiaWF0IjoxNTQ5MTA1OTUzLCJleHAiOjE1NTc3NDU5NTN9.hcc8pGRAEYxe0BlRdH6F08JpgdPvkLUZ1_7C660FicQ";
		when(service.saveNewsContent(newsContent)).thenReturn(true);
		mvc.perform(post("/api/v1/newsservice/news").header("authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(jsonToString(newsContent))).andExpect(status().isCreated());
		verify(service, times(1)).saveNewsContent(Mockito.any(NewsData.class));
		verifyNoMoreInteractions(service);
	}
	
	//Negative scenario test case while saving the news-content
	@Test
	public void testSaveNewsContentFailure() throws Exception{
		when(!service.saveNewsContent(newsContent)).thenReturn(false);
		verifyNoMoreInteractions(service);
	}

	@Test
	public void testDeleteNewsContentById() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbWFsMTIzOkFkbWluIiwiaWF0IjoxNTQ5MTA1OTUzLCJleHAiOjE1NTc3NDU5NTN9.hcc8pGRAEYxe0BlRdH6F08JpgdPvkLUZ1_7C660FicQ";
		when(service.deleteNewsContentById(1)).thenReturn(true);
		mvc.perform(delete("/api/v1/newsservice/news/{id}", 1).header("authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(jsonToString(newsContent))).andExpect(status().isOk());
		verify(service, times(1)).deleteNewsContentById(1);
		verifyNoMoreInteractions(service);
	}
	
	//Negative scenario test case while deleting the news content
	@Test
	public void testDeleteNewsContentFailure() throws NewsContentNotFoundException{
		when(!service.deleteNewsContentById(1)).thenReturn(false);
		verifyNoMoreInteractions(service);
	}

	@Test
	public void testGetMyNewsContent() throws Exception {
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbWFsMTIzOkFkbWluIiwiaWF0IjoxNTQ5MTA1OTUzLCJleHAiOjE1NTc3NDU5NTN9.hcc8pGRAEYxe0BlRdH6F08JpgdPvkLUZ1_7C660FicQ";
		when(service.getMyNews("amal123")).thenReturn(newsContentLst);
		mvc.perform(get("/api/v1/newsservice/newsdetails").header("authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print());
		verify(service, times(1)).getMyNews("amal123");
		verifyNoMoreInteractions(service);
	}
	
	

	private static String jsonToString(final Object object) {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			result = "Json processing error";
		}
		return result;
	}
}
