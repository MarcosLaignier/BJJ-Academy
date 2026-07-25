import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth.guard';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/landing/landing-page').then((page) => page.LandingPage),
    title: 'Fênix Jiu-Jitsu',
  },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login-page').then((page) => page.LoginPage),
    title: 'Entrar | Fênix Jiu-Jitsu',
  },
  {
    path: 'area',
    loadComponent: () => import('./pages/area/area-page').then((page) => page.AreaPage),
    canActivate: [authGuard],
    title: 'Área logada | Fênix Jiu-Jitsu',
  },
  { path: '**', redirectTo: '' },
];
