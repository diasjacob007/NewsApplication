import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './modules/authentication/authentication.service';
import { MatButton } from '@angular/material/button';

//Used for creating the buttons with proper api call
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{

  public isLoggedIn:Boolean=false; 

  constructor(private auth:AuthenticationService, private routes:Router){
    //code change for fixing the login page header
    auth.getLoggedInName.subscribe(val => this.updatelogin(val))
  }

  //code change for fixing the login page header
  private updatelogin(val: boolean) : void{
    this.isLoggedIn = val;    
  }

  ngOnInit() {       
  }
  
  Logout(){
    this.auth.deleteToken();
    this.routes.navigate(['/login']);
  }  
}
