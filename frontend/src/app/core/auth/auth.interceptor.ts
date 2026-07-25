import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const token = inject(AuthService).sessao()?.token;
  const requisicaoDaApi = request.url.startsWith('http://localhost:8080/api/');
  const login = request.url.endsWith('/auth/login');

  if (!token || !requisicaoDaApi || login) {
    return next(request);
  }

  return next(request.clone({
    setHeaders: { Authorization: `Bearer ${token}` },
  }));
};
