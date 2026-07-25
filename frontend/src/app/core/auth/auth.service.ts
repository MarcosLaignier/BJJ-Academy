import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { inject, Injectable, PLATFORM_ID, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse, Sessao } from './auth.models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly platformId = inject(PLATFORM_ID);
  private readonly storageKey = 'fenix.sessao';

  readonly sessao = signal<Sessao | null>(this.lerSessao());

  login(dados: LoginRequest): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>('http://localhost:8080/api/auth/login', dados)
      .pipe(tap((resposta) => this.salvarSessao(resposta)));
  }

  logout(): void {
    this.sessao.set(null);
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.removeItem(this.storageKey);
    }
  }

  estaAutenticado(): boolean {
    return this.sessao() !== null;
  }

  private salvarSessao(resposta: LoginResponse): void {
    const sessao: Sessao = {
      token: resposta.token,
      pessoaId: resposta.pessoaId,
      nome: resposta.nome,
      perfil: resposta.perfil,
      permissoes: resposta.permissoes,
      trocaSenhaObrigatoria: resposta.trocaSenhaObrigatoria,
    };
    this.sessao.set(sessao);
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.setItem(this.storageKey, JSON.stringify(sessao));
    }
  }

  private lerSessao(): Sessao | null {
    if (!isPlatformBrowser(this.platformId)) {
      return null;
    }
    const valor = sessionStorage.getItem(this.storageKey);
    if (!valor) {
      return null;
    }
    try {
      return JSON.parse(valor) as Sessao;
    } catch {
      sessionStorage.removeItem(this.storageKey);
      return null;
    }
  }
}
