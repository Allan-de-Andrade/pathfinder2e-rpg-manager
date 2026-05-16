import { Component, inject } from '@angular/core';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Authentication } from '../../services/authentication';
import { Router } from '@angular/router';
import { TokenService } from '../../services/token/token-service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  imports: [
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  loginForm!: FormGroup
  email!: FormControl
  password!: FormControl
  submited: boolean = false
  tokenService = inject(TokenService)
  router = inject(Router)
  auth = inject(Authentication)
  constructor() {
    this.loginForm = new FormGroup({
      email: new FormControl("", [Validators.required, Validators.email]),
      password: new FormControl("", [Validators.required, Validators.minLength(8)]),
    })
    this.loginForm.valueChanges.subscribe(() => {
      this.submited = false
    })
  }
  submit(): void {
    this.submited = true
    
    if (this.loginForm.invalid) {
      return
    }

    const { email, password } = this.loginForm.value;
    this.auth.loginBasic({ username: "", email, password }).
      subscribe({
        
        next: (data) => {
          this.tokenService.set(data.token)
          this.router.navigate(['/'])
        },

        error: (err:HttpErrorResponse) => {
          if(err.status === 401){
            alert("Credenciais inválidas")
          }
          else{
            alert("Erro ao fazer login: " + err.status)
          }
        }
      })
  }
}
