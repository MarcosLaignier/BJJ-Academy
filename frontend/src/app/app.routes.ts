import { Routes } from '@angular/router';
import { authGuard, permissionGuard } from './core/auth/auth.guard';

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
    children: [
      {
        path: '',
        loadComponent: () => import('./pages/dashboard/dashboard-page').then((page) => page.DashboardPage),
        title: 'Área logada | Fênix Jiu-Jitsu',
      },
      {
        path: 'perfis',
        canActivate: [permissionGuard('PERFIL_GERENCIAR')],
        children: [
          {
            path: '',
            loadComponent: () =>
              import('./pages/perfis-acesso/perfis-acesso-page').then((page) => page.PerfisAcessoPage),
            title: 'Perfis de acesso | Fênix Jiu-Jitsu',
          },
          {
            path: 'novo',
            loadComponent: () =>
              import('./pages/perfis-acesso/perfil-acesso-form-page')
                .then((page) => page.PerfilAcessoFormPage),
            title: 'Novo perfil | Fênix Jiu-Jitsu',
          },
          {
            path: 'editar/:id',
            loadComponent: () =>
              import('./pages/perfis-acesso/perfil-acesso-form-page')
                .then((page) => page.PerfilAcessoFormPage),
            title: 'Editar perfil | Fênix Jiu-Jitsu',
          },
        ],
      },
      {
        path: 'faixas',
        canActivate: [permissionGuard('FAIXA_GERENCIAR')],
        children: [
          { path: '', loadComponent: () => import('./pages/faixas/faixas-page').then((page) => page.FaixasPage), title: 'Faixas | Fênix Jiu-Jitsu' },
          { path: 'editar/:id', loadComponent: () => import('./pages/faixas/faixa-form-page').then((page) => page.FaixaFormPage), title: 'Editar faixa | Fênix Jiu-Jitsu' },
        ],
      },
    ],
  },
  { path: '**', redirectTo: '' },
];
