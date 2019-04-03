import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { News } from '../../news';
import { NewsService } from '../../news.service';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'news-watchlist',
  templateUrl: './watchlist.component.html'
})
//This component class is used for handling all the watch-listed news details
//corresponding to one particular user
export class WatchlistComponent implements OnInit {
  newsArray: Array<News>;
  useWatchlistApi=true;
   
  constructor(private httpClient : HttpClient,private newsService: NewsService,private matSnackBar
  : MatSnackBar) { 
    this.newsArray=[];    
  }

  ngOnInit() {
    let message='Favourite News List is Empty';
        this.newsService.getMyWatchList().subscribe((newsArray)=> {
         if(newsArray.length===0){
           this.matSnackBar.open(message,'',{
                duration:1000
           });
         }
         this.newsArray.push(...newsArray);
      });
  }

}
