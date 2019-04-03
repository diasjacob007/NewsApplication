package com.stackroute.newsapp.controller;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.newsapp.domain.NewsData;
import com.stackroute.newsapp.exceptions.NewsContentAlreadyExistsException;
import com.stackroute.newsapp.exceptions.NewsContentNotFoundException;
import com.stackroute.newsapp.service.NewsContentServiceIntrf;

import io.jsonwebtoken.Jwts;

/*
 * Controller class for handling all the incoming requests
 * Date : 01.26.2019
 * 
 * */
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/v1/newsservice")
public class NewsContentController {

	private NewsContentServiceIntrf newsContentService;

	@Autowired
	public NewsContentController(final NewsContentServiceIntrf newsContentService) {
		this.newsContentService = newsContentService;
	}

	/*
	 * Method for handling the controller part for saving new news content
	 * object-01.26.2019 This can be performed by both Admin and User-02.02.2019
	 */
	@PostMapping("/news")
	public ResponseEntity<?> saveNewMovie(@RequestBody NewsData newsdata, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<?> responseEntity;
		final String authHeader = request.getHeader("authorization");
		final String token = authHeader.substring(7);
		System.out.println("token is: " + token);
		int i = token.lastIndexOf('.');
		String tokenwithoutSignature = token.substring(0, i + 1);
		String jwtTokenSubject = Jwts.parser().setSigningKey("ThisIsASecret").parseClaimsJwt(tokenwithoutSignature)
				.getBody().getSubject();
		System.out.println("Now the jwtTokenSubject is: " + jwtTokenSubject);
		String[] userAuth = jwtTokenSubject.split(":");
		String userId = userAuth[0].toString();
		String userRole = userAuth[1].toString();
		System.out.println(
				"Inside saveNewMovie()...UserId is: " + userId.toString() + " and Role is: " + userRole.toString());
		/*
		 * If not changed to Base64, exception is: io.jsonwebtoken.SignatureException:
		 * JWT signature does not match locally computed signature. JWT validity cannot
		 * be asserted and should not be trusted.
		 * 
		 * If changed to Base64, exception is: io.jsonwebtoken.UnsupportedJwtException:
		 * Signed Claims JWSs are not supported.
		 */
		try {
			newsdata.setUserId(userId);
			newsdata.setUserRole(userRole);
			newsContentService.saveNewsContent(newsdata);
			responseEntity = new ResponseEntity<NewsData>(newsdata, HttpStatus.CREATED);
		} catch (NewsContentAlreadyExistsException e) {
			responseEntity = new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\")",
					HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

	/*
	 * Method for updating already existing news contents 01.26.2019 This action can
	 * be performed by only the Administrator-02.02.2019
	 */
	@PutMapping(path = "/news/{id}")
	public ResponseEntity<?> updateNewsContent(@PathVariable("id") final Integer id, @RequestBody NewsData newsdata) {
		ResponseEntity<?> responseEntity = null;
		try {
			String userRole = newsdata.getUserRole().toString();
			System.out.println("Inside updateNewsContent() userRole is: " + userRole.toString());
			if (userRole.equals("Admin")) {
				final NewsData retrievedNewsContent = newsContentService.updateNewsContent(newsdata);
				responseEntity = new ResponseEntity<NewsData>(retrievedNewsContent, HttpStatus.OK);
			} else {
				String httpStatusMessage = "Admin can only update a news content";
				responseEntity = new ResponseEntity<String>("{\"message\":\"" + httpStatusMessage + "\")",
						HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (NewsContentNotFoundException e) {
			responseEntity = new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\")",
					HttpStatus.NOT_FOUND);// Updated Status code
		}
		return responseEntity;
	}

	/*
	 * Method for deleting one particular news content 01.26.2019 This action can be
	 * performed by both Admin and the User
	 */
	@DeleteMapping(value = "/news/{id}")
	public ResponseEntity<?> deleteNewsContentsById(@PathVariable("id") final int id) {
		ResponseEntity<?> responseEntity;
		try {
			newsContentService.deleteNewsContentById(id);
		} catch (NewsContentNotFoundException e) {
			responseEntity = new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\")",
					HttpStatus.NOT_FOUND);
		}
		responseEntity = new ResponseEntity<String>("Successfully deleted the Movie", HttpStatus.OK);
		return responseEntity;
	}

	/*
	 * Method for fetching one particular news content based on the news id
	 * 01.26.2019
	 * 
	 */
	@GetMapping(path = "/news/{id}")
	public ResponseEntity<?> fetchnewsContentBasedOnId(@PathVariable("id") final int id) {
		ResponseEntity<?> responseEntity;
		NewsData thisNewsDataObject = null;
		try {
			thisNewsDataObject = newsContentService.getNewsContentById(id);
		} catch (NewsContentNotFoundException e) {
			responseEntity = new ResponseEntity<String>("{\"message\":\"" + e.getMessage() + "\")",
					HttpStatus.NOT_FOUND);
		}
		responseEntity = new ResponseEntity<NewsData>(thisNewsDataObject, HttpStatus.OK);
		return responseEntity;
	}

	/*
	 * Method for fetching all the news details based on the Loggined user
	 * 01.26.2019
	 */
	@GetMapping("/newsdetails")
	public @ResponseBody ResponseEntity<List<NewsData>> fetchMyNewsDetails(final ServletRequest req,
			final ServletResponse res) {

		final HttpServletRequest request = (HttpServletRequest) req;
		final String authHeader = request.getHeader("authorization");
		final String token = authHeader.substring(7);
		int i = token.lastIndexOf('.');
		String tokenwithoutSignature = token.substring(0, i + 1);
		String jwtTokenSubject = Jwts.parser().setSigningKey("ThisIsASecret").parseClaimsJwt(tokenwithoutSignature).getBody()
				.getSubject();
		String[] userAuth = jwtTokenSubject.split(":");
		String userId = userAuth[0].toString();
		System.out.println("Inside fetchMyNewsDetails() userId is: " + userId);
		return new ResponseEntity<List<NewsData>>(newsContentService.getMyNews(userId), HttpStatus.OK);
	}
}
