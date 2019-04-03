import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { News } from '../../news';
import { NewsService } from '../../news.service';
import { NewsDialogComponent } from '../newsdialog/newsdialog.component';
import { AuthenticationService } from '../../../authentication/authentication.service';

import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'news-thumbnail',
  templateUrl: './thumbnail.component.html',
  styleUrls: [
    './thumbnail.component.css'
    ]
})
export class ThumbnailComponent implements OnInit {

  @Input()
  news: News;
  @Input()
  useWatchlistApi:boolean;
  @Output()
  addNews=new EventEmitter();
  @Output()
  deleteNews=new EventEmitter();
  jwtToken:string;
  isUpdateButtonVisibile:boolean;
  isDeleteButtobVisible:boolean;

  wholenewsArray: Array<News>;
  watchlistnewsArray: Array<News>;
  newsType: string;
  isDisabled: boolean; 
  
  constructor(private snackBar : MatSnackBar, private dialog : MatDialog,
    private authService:AuthenticationService,
    private newsService: NewsService, private route: ActivatedRoute) { 
      
      this.route.data.subscribe((data)=>{
        this.wholenewsArray=[];
        this.newsType=data.newsType;
      }); 


    this.jwtToken=this.authService.gettokenVal();
    var jwtData=this.jwtToken.split('.')[1];
    var decodedjwtJSONData=window.atob(jwtData);
    var decodedJWTData=JSON.parse(decodedjwtJSONData);
    var jwtTokenSub=decodedJWTData.sub;
    var userRole=jwtTokenSub.split(':')[1];
    if(userRole=='RegUser'){
      this.isDeleteButtobVisible=true;
      this.isUpdateButtonVisibile=false;
    }else{
      this.isDeleteButtobVisible=true;
      this.isUpdateButtonVisibile=true;
    }     
    
    //Code fix for the prevention of adding favourite news multiple times into the FavouritesNewsList
    this.wholenewsArray=[];
    this.watchlistnewsArray=[]; 
    this.newsService.getNews(this.newsType).subscribe(
      
      (wholenewsArray)=> {
        this.wholenewsArray.push(...wholenewsArray);
        this.newsService.getMyWatchList().subscribe((watchlistnewsArray)=> {
        if(watchlistnewsArray.length===0){
          console.log('No movie detail exists inside the watchlist');
        }else{
          this.watchlistnewsArray.push(...watchlistnewsArray);
            for(var j=0;j<this.watchlistnewsArray.length;j++){ 
                if(this.news.title=== this.watchlistnewsArray[j].title){
                this.isDisabled=true;
                }
              }             
        }        
      });
    });
    //End code fix for the prevention of adding favourite news multiple times into the FavouritesNewsList
  }
  ngOnInit() {       
     
  }

  //calling the parent add to watch list component i.e the container component
  addToWatchList(){
      this.addNews.emit(this.news); 
      this.isDisabled=true; 
  }
  //calling the parent delete from watch list component i.e the container component
  deleteFromWatchList(){
    this.deleteNews.emit(this.news);
  }
  //calling the parent update watch list component i.e the container component
  updateFromWatchList(actionType)
  {
    let dialogRef = this.dialog.open(NewsDialogComponent,{
      width : '400px',
      data : {obj : this.news, actionType : actionType}
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
