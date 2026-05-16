import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { OAuthEvent, OAuthService } from 'angular-oauth2-oidc';
import { filter, Observable } from 'rxjs';
import { TokenService } from './token/token-service';

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
  back_end_url = "http://localhost:8080/api/user"
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
    return this.http.post(this.back_end_url + "/register", user);
  }
  loginBasic(user: User): Observable<any> {
    return this.http.post(this.back_end_url + "/login", user, { withCredentials: true });
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
      .post<{ token: string; expirationTime: number }>(
        `${this.back_end_url}/google-login`,
        { idToken }
      )
      .subscribe({
        next: (response) => {
          this.tokenService.set(response.token);
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
