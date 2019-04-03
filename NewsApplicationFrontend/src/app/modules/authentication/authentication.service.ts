import { Observable } from 'rxjs';
import { Injectable, Output, EventEmitter } from '@angular/core';
import * as jwt_decode from 'jwt-decode';
import { HttpClient } from '@angular/common/http';
export const TOKEN_NAME:string='jwt_token';

@Injectable()
export class AuthenticationService{
    //code change for fixing the login page header
    @Output() getLoggedInName = new EventEmitter();
    service_end_point:string;
    tokenVal:string;
    //code change for fixing the login page header
    loggedIn:boolean;

    constructor(private httpClient:HttpClient){
        this.service_end_point='http://localhost:8089/api/v1/userservice';
    }
    //Method for registering one new User
    registerUser(newUser){
        const url=this.service_end_point+"/register";        
        return this.httpClient.post(url,newUser,{responseType:'text'});
    }
    //Method for login
    loginUser(newUser){
        const url=this.service_end_point+"/login";
        
        return this.httpClient.post(url,newUser);
    }
    //Method for setting new token value
    settokenVal(tokenVal:string){
        return localStorage.setItem(TOKEN_NAME,tokenVal);
    }
    //Method for getting the token value
    gettokenVal(){
        
        return localStorage.getItem(TOKEN_NAME);
    }
    //Method for removing the token while logout
    deleteToken(){
        this.loggedIn = false;
        this.getLoggedInName.emit(this.loggedIn);
        return localStorage.removeItem(TOKEN_NAME);

    }
    
    //Method for finding the expiration date of the Token
    getTokenExpirationDate(tokenVal:string):Date{
        const decoded=jwt_decode(tokenVal);
        if(decoded.exp === undefined) return null;
        const date=new Date(0);
        date.setUTCSeconds(decoded.exp);
        return date;
    }
    //Method for checking whether the token is expired or not
    isTokenExpired(tokenVal?:string):boolean{
        if(!tokenVal) tokenVal=this.gettokenVal();
        if(!tokenVal) return true;
        const date=this.getTokenExpirationDate(tokenVal);
        if(date===undefined || date==null) return false;
        return !(date.valueOf() > new Date().valueOf());
    }

    isAuthenticated(): boolean {
        const token = localStorage.getItem('jwt_token');
        this.loggedIn = !!localStorage.getItem('jwt_token');

        if(!this.isTokenExpired(token)){
            this.getLoggedInName.emit(this.loggedIn);
        }
        
        // Check whether the token is expired and return
        // true or false
        return !this.isTokenExpired(token);
      }
}