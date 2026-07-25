import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-area-page',
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
}
