import { RouterModule, Routes} from '@angular/router';
import { NewsModule } from './modules/news/news.module';
import { AuthenticationModule } from './modules/authentication/authentication.module'
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { Route } from '@angular/compiler/src/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule, MatButton } from '@angular/material/button';
import { MatSelectModule} from '@angular/material';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { AuthguardService } from './authGuard.service';

 const appRoutes : Routes = [
 {
     path:'',
     redirectTo:'/login',
     pathMatch:'full'
 }
];
@NgModule({
  declarations: [
    AppComponent   
  ],
  imports: [
    NewsModule,
    BrowserModule,
    AuthenticationModule,
    FormsModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatDialogModule,
    MatSelectModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [AuthguardService],  
  bootstrap: [AppComponent]
})
export class AppModule { }
