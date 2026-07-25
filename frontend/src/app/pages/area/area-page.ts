import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
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
  private readonly router = inject(Router);

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
