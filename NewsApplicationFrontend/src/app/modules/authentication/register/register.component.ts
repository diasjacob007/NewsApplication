import { AuthenticationService } from './../authentication.service';
import { OnInit, Component } from '@angular/core';
import { User } from './../User';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

@Component({
    selector:'app-register',
    templateUrl:'./register.component.html',
    styleUrls:['./register.component.css']
})

export class RegisterComponent implements OnInit{

    newUser:User;
    

    constructor(private authService:AuthenticationService , private router:Router){
        this.newUser = new User();        
    }

    ngOnInit()
    { }
    //Function implementation for registering a new user
    registerUser(form:NgForm)
    {
        console.log('Register User' , this.newUser.userId, this.newUser.firstname);
        this.newUser.userRole=form.controls['userRole'].value;
        console.log('Registered users Role', this.newUser.userRole);
        console.log('new user' , this.newUser);
        this.authService.registerUser(this.newUser).subscribe((data)=>{
            console.log('User Data is: ' , data);
            this.router.navigate(['/login']);
        })
    }
    //function implementation for resetting the input register form
    resetInput(){
        this.newUser = new User();
        this.newUser.firstname="";
        this.newUser.lastname="";
        this.newUser.userId="";
        this.newUser.userId="";
    }
}