import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Authentication } from '../../services/authentication';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule
  ],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  registerForm!:FormGroup
  submited:boolean = false

  constructor(public auth:Authentication,public router:Router) {
    this.registerForm = new FormGroup({
      username:new FormControl("",[Validators.required,Validators.minLength(4)]),
      email: new FormControl("",[Validators.required,Validators.email]),
      password: new FormControl("",[Validators.required,Validators.minLength(8)]),
    })
    this.registerForm.valueChanges.subscribe(() =>{
      this.submited = false
    })
  }
  submit(){
    this.submited = true
    if(this.registerForm.invalid)
      return

    const {username,email,password} = this.registerForm.value
    this.auth.register({username:username,email:email,password:password}).
    subscribe({next:(data)=>{
      console.log(data)
      this.router.navigate(['/login'])
    },
    error:(err)=>{
      if(err.status === 409){
        alert("Usuário com esse email já existente")
        return
      }
      else if(err.status === 400){
        alert("Dados inválidos")
        return
      }
      alert("Erro ao registrar o usuário: " + err.error.message)
    }
  })
  }
}
