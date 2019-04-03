import { AuthenticationService } from './modules/authentication/authentication.service';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router/src/interfaces';

@Injectable()
export class AuthguardService implements CanActivate{

    constructor(private route:Router,private authService:AuthenticationService){}

    canActivate(): boolean {
        if (!this.authService.isAuthenticated()) {
          this.route.navigate(['login']);
          return false;
        }
        return true;
      }
}