package com.stackroute.newsapp.domain;

/*
 * This is the POJO class for setting all the details
 * related with News_01.26.2019
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "newsdetails")
public class NewsData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "author")
	private String author;

	@Column(name = "title")
	private String title;

	@Column(name = "description",length=100000)
	private String description;
	
	@Column(name = "url")
	private String url;

	@Column(name = "urlToImage")
	private String urlToImage;

	@Column(name = "publishedAt")
	private String publishedAt;

	@Column(name = "content",length=100000)
	private String content;

	@Column(name = "userId")
	private String userId;
	
	@Column(name = "userRole")
	private String userRole;

	public NewsData() {
		super();
	}

	public NewsData(int id,String author, String title, String description, String url,
			String urlToImage, String publishedAt, String content, String userId, String userRole) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.description = description;
		this.url = url;
		this.urlToImage = urlToImage;
		this.publishedAt = publishedAt;
		this.content = content;
		this.userId = userId;
		this.userRole = userRole;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrlToImage() {
		return urlToImage;
	}

	public void setUrlToImage(String urlToImage) {
		this.urlToImage = urlToImage;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
