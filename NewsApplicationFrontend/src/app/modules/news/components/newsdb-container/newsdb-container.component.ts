import { Component, OnInit } from '@angular/core';
import { News } from '../../news';
import { NewsService } from '../../news.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'newsapi-container',
  template: `
    <news-container [newsArray]="newsArray"></news-container>
  `,
  styles: []
})
//Component class for holding news api list
export class NewsdbContainerComponent implements OnInit {
  newsArray: Array<News>;
  newsType: string;

  constructor(private newsService: NewsService, private route: ActivatedRoute) { 
    this.route.data.subscribe((data)=>{
      this.newsArray=[];
      this.newsType=data.newsType;
    });
  }

  ngOnInit() {
    this.newsService.getNews(this.newsType).subscribe(
      (newsArray)=> {
        this.newsArray.push(...newsArray);
      });
  }

}
