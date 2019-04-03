import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContainerComponent } from './components/container/container.component';
import { NewsdbContainerComponent } from './components/newsdb-container/newsdb-container.component';
import { WatchlistComponent } from './components/watchlist/watchlist.component';
import { SearchComponent } from './components/search/search.component';
import { AuthguardService } from '../../authGuard.service';

//This is the router module which is used for routing the application based on the incoming
//requests. While simply hitting the localhost:4200 the application will redirect to all the 
//trending new from the newsapi. If the url is appended with /category_wise, it will show all the
//category_wise news and the url is appended with /watchlist it will display all the news that 
//are added into the MyWatchList segment. If the url is appened with /search, the application redirects to
//the search segment in the application. There one user can easily search various news details based on 
//there interest.
const newsRoutes: Routes=[
{
    path: 'news',
    children: [
        {
            path: '',
            redirectTo: '/news/trending_wise',
            pathMatch: 'full',
            canActivate: [AuthguardService]
        },
        {
            path: 'category_wise',
            component: NewsdbContainerComponent,
            canActivate: [AuthguardService],
            data: {
                 newsType : 'category_wise'       
            },
        },
        {
            path: 'trending_wise',
            component: NewsdbContainerComponent,
            canActivate: [AuthguardService],
            data: {
                newsType : 'trending_wise',
            }
        },
        {
            path: 'watchlist',
            component: WatchlistComponent,
            canActivate: [AuthguardService]
        },
        {
            path: 'search',
            component: SearchComponent,
            canActivate: [AuthguardService]
        }
    ]
  }
];

@NgModule({
    imports: [
        RouterModule.forChild(newsRoutes),
    ],
    exports: [
        RouterModule,
    ]
})

export class NewsRouterModule{}