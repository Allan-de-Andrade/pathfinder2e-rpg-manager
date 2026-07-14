import { HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import {TokenService} from '../services/token/token-service'
import { inject } from '@angular/core';
import { catchError, switchMap, throwError } from 'rxjs';

let isRefreshing = false

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {

  const tokenService:TokenService = inject(TokenService) 
  const API_URL = 'http://localhost:8080/api';

  if(!req.url.startsWith(API_URL)){
    return next(req)
  }
  const PUBLIC_ROUTES = [
    '/api/user/register',
    '/api/auth/login',
    '/api/auth/google-login',
    '/api/auth/refresh-token'
  ];
  let is_public = PUBLIC_ROUTES.some(route => req.url.includes(route))
  let cloned_req = req 
  if(is_public){
    return next(req)
  }

  let token = tokenService.get();

  if (token) {
    const cloned = req.clone({
      setHeaders:{
        Authorization:`Bearer ${token}`
      }
    })
    return next(cloned);
  }

  return next(cloned_req).pipe(
    catchError((error: HttpResponse<any>) => {
      if (error.status === 401 && !isRefreshing) {
        isRefreshing = true;
        return tokenService.refreshToken().pipe(
          switchMap((response: any) => {

            tokenService.set(response.access_token);

            const retryReq = req.clone({
              setHeaders: {
                Authorization: `Bearer ${response.access_token}`
              },
              withCredentials: true
            });

            return next(retryReq);
          }),
        )
      }
      return throwError(() => error);
    })
  );
}
