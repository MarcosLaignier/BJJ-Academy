import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { finalize } from 'rxjs';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'login-page',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginPage {
  private readonly fb = inject(FormBuilder);
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly carregando = signal(false);
  protected readonly erro = signal<string | null>(null);
  protected readonly formulario = this.fb.nonNullable.group({
    email: ['admin@fenix.local', [Validators.required, Validators.email]],
    senha: ['', Validators.required],
  });

  protected entrar(): void {
    if (this.formulario.invalid || this.carregando()) {
      this.formulario.markAllAsTouched();
      return;
    }
    this.erro.set(null);
    this.carregando.set(true);
    this.auth
      .login(this.formulario.getRawValue())
      .pipe(finalize(() => this.carregando.set(false)))
      .subscribe({
        next: () => void this.router.navigateByUrl('/area'),
        error: () => this.erro.set('E-mail ou senha inválidos. Confira os dados e tente novamente.'),
      });
  }
}
