import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators/map';
import { News } from './news';
import { Observable } from 'rxjs/Observable';
import { retry } from 'rxjs/operators';

//All the news related services are defined in this service class
@Injectable()
export class NewsService {

  newsapiEndpoint : string; 
  imagePrefix : string;
  apiKey : string;
  watchlistEndPoint : string;
  service_end_point: string;
  search: string;
  categorynewstype: string;
  trendingnewstype: string;
  endPoint: string;

  constructor(private http : HttpClient) { 
    this.apiKey='apikey=b30065de40a543b999bc7671941e639b';
    this.newsapiEndpoint='https://newsapi.org/v2/top-headlines?';
    this.service_end_point='http://localhost:8081/api/v1/newsservice';
    this.search='https://newsapi.org/v2/everything?';
    this.categorynewstype='category=business';
    this.trendingnewstype='country=in';
    this.endPoint='';
    
  }

  //Method for fetching all the news related details from the newsapi
  getNews(type : string, page: number=1): Observable<Array<News>>{
    console.log('Inside getNews() type is: ',type);
    if(type=='category_wise')
    {
      this.endPoint=`${this.newsapiEndpoint}${this.categorynewstype}&${this.apiKey}&page=${page}`;    
    }else if(type=='trending_wise'){
      this.endPoint=`${this.newsapiEndpoint}${this.trendingnewstype}&${this.apiKey}&page=${page}`; 
    }
    return this.http.get(this.endPoint).pipe(
      retry(3),
      map(this.pickNewsResults)
    );
  }
  //Method for getting the list of news details from the service
  pickNewsResults(response){
    return response['articles']; 
  }

  
   //Method for saving news data into the news favourite list
  saveWatchListNews(news){
      const url=this.service_end_point+"/news";
      return this.http.post(url, news);
  }

  //Calling the service_end_point for retrieving all the favourite list news details
  getMyWatchList() 
  {
    const url=this.service_end_point+"/newsdetails";
    return this.http.get<Array<News>>(url);
  }

  //Method for deleting news from the News FavouriteList
  deleteFromMyWatchList(news : News){
    const url = `${this.service_end_point}/news/${news.id}`;
    return this.http.delete(url,{responseType:'text'});
  }

  //Method for updating the favourite news comments
  updateNewsDescription(news){
    const url = `${this.service_end_point}/news/${news.id}`;
    return this.http.put(url,news);
  }

  //Method for searching one particular news from the newsapi service
  searchNewsDetails(searchKey:string) : Observable<Array<News>>{
    if(searchKey.length > 0){    
      const url=`${this.search}q=${searchKey}&${this.apiKey}&language=en&page=1`;
      return this.http.get(url).
      pipe(retry(3),
      map(this.pickNewsResults.bind(this)));
    }
  }
}
