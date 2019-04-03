import { AuthenticationService } from './../authentication/authentication.service';
import { Injectable } from '@angular/core';
import{
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor
}from '@angular/common/http';

import { Observable } from 'rxjs/Observable';

@Injectable()
export class TokenInterceptor implements HttpInterceptor{

    constructor(private auth:AuthenticationService){

    }
        intercept(request:HttpRequest<any>,next:HttpHandler):Observable<HttpEvent<any>>{
            console.log('Inside intercept()');
            request = request.clone({
                setHeaders:{
                    authorization: `Bearer ${this.auth.gettokenVal()}`
                }
            });
            return next.handle(request);
        }
}