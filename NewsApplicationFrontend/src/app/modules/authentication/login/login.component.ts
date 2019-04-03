import { AuthenticationService } from './../authentication.service';
import { OnInit, Component, Input } from '@angular/core';
import { User } from './../User';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector:'app-login',
    templateUrl:'./login.component.html',
    styleUrls:['./login.component.css']
})

export class LoginComponent implements OnInit{

    newUser:User;
    
    constructor(private authService:AuthenticationService , private router:Router){
        console.log('Inside OnInit...of LoginComponent');
        this.newUser = new User();            
    }
    
    ngOnInit()
    {

    }
    loginUser()
    {
        console.log('Loggined User' , this.newUser.userId, this.newUser.password);
        this.authService.loginUser(this.newUser).subscribe((data)=>{
            if(data['token']){
                this.authService.settokenVal(data['token']);
                this.router.navigate(['/news/trending_wise']);
                console.log('After navigate()...');
            }
        })
    }
}