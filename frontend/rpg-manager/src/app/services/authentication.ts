import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { OAuthEvent, OAuthService } from 'angular-oauth2-oidc';
import { filter, Observable } from 'rxjs';
import { TokenService } from './token/token-service';
import { User } from '../components/login/User';

const oAuthConfiguration = {
  issuer: "https://accounts.google.com",
  redirectUri: window.location.origin,
  clientId: '711221823737-p3inlo84i21q73gj0gqo0ihfnii277ug.apps.googleusercontent.com',
  scope: 'openid profile email',
  strictDiscoveryDocumentValidation: false,
}
@Injectable({
  providedIn: 'root',
})
export class Authentication {
  private readonly googleLoginPendingKey = 'google-login-pending';
  private googleLoginInProgress = false;
  
  user_url = "http://localhost:8080/api/user"
  auth_url='http://localhost:8080/api/auth'

  router = inject(Router)
  http = inject(HttpClient)
  tokenService = inject(TokenService)
  constructor(private oauthService: OAuthService) {
    this.configure();
  }

  private configure() {
    this.oauthService.configure(oAuthConfiguration);
    this.oauthService.loadDiscoveryDocumentAndTryLogin().then(() => {
      if (this.hasPendingGoogleLogin() && this.oauthService.hasValidIdToken()) {
        this.sendGoogleToken();
      }
    });
    this.oauthService.events
        .pipe(filter((event: OAuthEvent) => event.type === 'token_received'))
        .subscribe(() => {
          if (this.hasPendingGoogleLogin()) {
            this.sendGoogleToken();
          }
        });
  }
  
  register(user: User) {
    return this.http.post(this.user_url + "/register", user);
  }
  loginBasic(user: User): Observable<{ access_token: string }> {
    return this.http.post<{ access_token: string }>(this.auth_url + "/login", user, { withCredentials: true });
  }
  loginGoogle() {
    sessionStorage.setItem(this.googleLoginPendingKey, 'true');
    this.oauthService.initCodeFlow();
  }

  logout() {
    this.oauthService.logOut();
  }

  public sendGoogleToken():void {
    if (this.googleLoginInProgress || !this.hasPendingGoogleLogin()) {
      return
    }

    const idToken = this.oauthService.getIdToken();
    if (!idToken) {
      return
    }

    this.googleLoginInProgress = true;
    this.http
      .post<{ access_token: string;}>(
        `${this.auth_url}/google-login`,
        { idToken },
        {withCredentials:true}
      )
      .subscribe({
        next: (response) => {
          this.tokenService.set(response.access_token);
          this.clearPendingGoogleLogin();
          this.oauthService.logOut(true);
          this.router.navigate(['/']);
        },

        error: (error) => {
          this.clearPendingGoogleLogin();
          console.error('Google login failed', error);
          alert('Erro ao fazer login com Google' );
        },
      });
  }
  
  get isLoggedIn(): boolean {
    return this.tokenService.get() !== null;
  }

  get userProfile() {
    return this.oauthService.getIdentityClaims();
  }

  private hasPendingGoogleLogin(): boolean {
    return sessionStorage.getItem(this.googleLoginPendingKey) === 'true';
  }

  private clearPendingGoogleLogin(): void {
    this.googleLoginInProgress = false;
    sessionStorage.removeItem(this.googleLoginPendingKey);
  }
}
