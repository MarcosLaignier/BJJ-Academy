import { ChangeDetectionStrategy, Component, HostListener, PLATFORM_ID, afterNextRender, inject, signal } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'area-page',
  imports: [RouterLink, RouterLinkActive, RouterOutlet],
  templateUrl: './area-page.html',
  styleUrl: './area-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AreaPage {
  protected readonly auth = inject(AuthService);
  protected readonly secaoAberta = signal<'configuracoes' | 'cadastros' | 'operacoes' | null>('configuracoes');
  protected readonly barraRecolhida = signal(false);
  protected readonly menuMobileAberto = signal(false);
  protected readonly mobile = signal(false);
  private readonly router = inject(Router);
  private readonly browser = isPlatformBrowser(inject(PLATFORM_ID));

  constructor() {
    afterNextRender(() => {
      this.atualizarViewport();
      this.barraRecolhida.set(localStorage.getItem('bjj-sidebar-collapsed') === 'true');
    });
  }

  @HostListener('window:resize')
  protected atualizarViewport(): void {
    if (this.browser) {
      this.mobile.set(window.innerWidth <= 760);
    }
  }

  protected alternarMenu(): void {
    if (this.mobile()) {
      this.menuMobileAberto.update((aberto) => !aberto);
      return;
    }
    this.barraRecolhida.update((recolhida) => !recolhida);
    if (this.browser) {
      localStorage.setItem('bjj-sidebar-collapsed', String(this.barraRecolhida()));
    }
  }

  protected alternarSecao(secao: 'configuracoes' | 'cadastros' | 'operacoes'): void {
    if (this.barraRecolhida()) {
      this.barraRecolhida.set(false);
      if (this.browser) {
        localStorage.setItem('bjj-sidebar-collapsed', 'false');
      }
    }
    this.secaoAberta.update((atual) => atual === secao ? null : secao);
  }

  protected fecharMenuMobile(): void {
    this.menuMobileAberto.set(false);
  }

  protected sair(): void {
    this.auth.logout();
    void this.router.navigateByUrl('/login');
  }

  protected podeGerenciarPerfis(): boolean {
    return this.auth.sessao()?.permissoes.includes('PERFIL_GERENCIAR') ?? false;
  }

  protected podeGerenciarFaixas(): boolean {
    return this.auth.sessao()?.permissoes.includes('FAIXA_GERENCIAR') ?? false;
  }
}
