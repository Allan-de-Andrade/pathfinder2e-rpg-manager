import { inject, Injectable } from '@angular/core';
import {LocalStorageToken} from './localStorageToken'
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private readonly key = "auth-token"
  localStorage = inject(LocalStorageToken)
  http = inject(HttpClient);
  auth_url: string = "http://localhost:8080/api/auth";
  
  set(token:string): void{
    localStorage.setItem(this.key, token)
  }

  get():string | null{
    const token =  localStorage.getItem(this.key) || '';
    
    if(token == '')
      return null;
    
    if(this.isExpired(token)){
      this.remove()
      return null
    }

    return token
  }

  remove(){
    return localStorage.removeItem(this.key)
  }
  
  private isExpired(token: string): boolean {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      const expiry = payload.exp * 1000; // converte para ms
      return Date.now() > expiry;
    } 
    catch (e) {
      return true; // se não conseguir decodificar, considera expirado
    }
  }

  public refreshToken(): Observable<any> {
    return this.http.post(this.auth_url + "/refresh-token", {}, { withCredentials: true });
  }
}

