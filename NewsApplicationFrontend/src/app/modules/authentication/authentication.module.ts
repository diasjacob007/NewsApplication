import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule, MatButton } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule, MatIcon, MatFormField, MatAccordion } from '@angular/material';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { RegisterComponent } from './register/register.component';
import { AuthenticationService } from './authentication.service';
import { AuthenticationRoutingModule } from './authentication-router.module';
import { LoginComponent } from './login/login.component';

@NgModule({
    imports: [
      CommonModule,
      FormsModule,
      AuthenticationRoutingModule,
      MatFormFieldModule,      
      MatInputModule,
      MatButtonModule,
      MatIconModule,
      MatCardModule
    ],
  declarations:[RegisterComponent,LoginComponent],
  providers:[
    AuthenticationService
  ],
  exports:[RegisterComponent,LoginComponent]
})

export class AuthenticationModule{}
