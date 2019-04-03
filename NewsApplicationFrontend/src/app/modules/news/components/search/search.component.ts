import { Component, OnInit, Input, Inject } from '@angular/core';
import { News } from '../../news';
import { NewsService } from '../../news.service';

@Component({
    selector: 'news-search',
    templateUrl: './search.component.html',
    styleUrls: [
      './search.component.css'
      ]
  })
//Component class for performing all the search operations 
export class SearchComponent implements OnInit {

    newsArray:Array<News>;

    constructor(private newsService : NewsService){

    }

    ngOnInit() {     
    }

    onEnter(searchKey){
        this.newsService.searchNewsDetails(searchKey).subscribe(newsArray=>{
            this.newsArray=newsArray;
        });
    }
}