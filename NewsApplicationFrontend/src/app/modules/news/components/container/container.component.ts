import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Input } from '@angular/core';
import { News } from '../../news';
import { NewsService } from '../../news.service';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'news-container',
  templateUrl: './container.component.html',
  styleUrls: [
    './container.component.css'
  ]
})
export class ContainerComponent implements OnInit {
  
  @Input()
  newsArray: Array<News>;  
  @Input()
  useWatchlistApi:boolean;
  
  constructor(private httpClient : HttpClient,private newsService: NewsService,private matSnackBar
    : MatSnackBar) {     
  }

  ngOnInit() {
  }
  //logic for adding new news data into the watchlist
  addNewsToWatchList(news){
    let message=`${news.title} added to news Favourite List`;
    this.newsService.saveWatchListNews(news).subscribe(news =>{
        this.matSnackBar.open(message,'',{
            duration:1000
        });
    });
   }
   //logic for deleting news data from the componenet
   deleteNewsFromWatchList(news){
     let message=`${news.title} successfully deleted from the news Favourite List`;
     for(var i=0;i<this.newsArray.length;i++){
       if(this.newsArray[i].title===news.title){
         this.newsArray.splice(i,1);
       }
     }
     //logic for deleting the news data from the back-end
     this.newsService.deleteFromMyWatchList(news).subscribe((news)=>{
        this.matSnackBar.open(message,'',{
            duration:1000
        });
     });
   }
}
