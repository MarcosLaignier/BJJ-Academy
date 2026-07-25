import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = (_route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  return auth.estaAutenticado()
    ? true
    : router.createUrlTree(['/login'], { queryParams: { retorno: state.url } });
};

export const permissionGuard = (permissao: string): CanActivateFn => () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  return auth.sessao()?.permissoes.includes(permissao)
    ? true
    : router.createUrlTree(['/area']);
};
