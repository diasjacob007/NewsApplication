import { TokenInterceptor } from './interceptor.service';
import { NewsService } from './news.service';
import { NgModule, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient,HttpClientModule,HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule, Routes} from '@angular/router';
import { ThumbnailComponent } from './components/thumbnail/thumbnail.component';
import { ContainerComponent } from './components/container/container.component';
import { NewsRouterModule } from './news-router.module';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { NewsdbContainerComponent } from './components/newsdb-container/newsdb-container.component';
import { NewsDialogComponent } from './components/newsdialog/newsdialog.component';
import { SearchComponent } from './components/search/search.component';
import { from } from 'rxjs/observable/from';

//This module is mainly intended for declaring as well as importing all the news related 
//modules
@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    NewsRouterModule,
    MatCardModule,
    MatButtonModule,
    MatSnackBarModule,
    MatDialogModule,
    FormsModule,
    MatInputModule
  ],
  declarations: [
    ThumbnailComponent,
    ContainerComponent,
    WatchlistComponent,
    NewsdbContainerComponent,
    NewsDialogComponent,
    SearchComponent
  ],
  exports: [
    NewsRouterModule,
    ThumbnailComponent,
    WatchlistComponent,
    ContainerComponent,
    NewsdbContainerComponent,
    NewsDialogComponent,
    SearchComponent
  ],
  entryComponents: [
    NewsDialogComponent
  ],
  providers: [NewsService,{
    provide:HTTP_INTERCEPTORS,
    useClass:TokenInterceptor,
    multi:true
  }
],  
})
export class NewsModule { }
