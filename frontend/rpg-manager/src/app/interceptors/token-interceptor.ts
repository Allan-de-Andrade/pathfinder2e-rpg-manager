import { HttpInterceptorFn } from '@angular/common/http';
import {TokenService} from '../services/token/token-service'
import { inject } from '@angular/core';

export const tokenInterceptor: HttpInterceptorFn = (req, next) => {
  const PUBLIC_ROUTES = ['/api/user/register','/api/user/login','/api/user/google-login']
  const tokenService:TokenService = inject(TokenService) 
  const is_public = PUBLIC_ROUTES.some(route => req.url.includes(route))
  
  if(is_public)
    return next(req)

  const token = tokenService.get();
  if (token) {
    const cloned = req.clone({
      setHeaders:{
        Authorization:`Bearer ${token}`
      }
    })
    return next(cloned);
  }

  return next(req);
}
