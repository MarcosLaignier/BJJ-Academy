import { Directive, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { CrudService } from './crud-service';

@Directive()
export abstract class BaseCrud<D, C, F, ID = number> {
  protected readonly carregando = signal(false);
  protected readonly salvando = signal(false);
  protected readonly erro = signal<string | null>(null);

  protected constructor(protected readonly service: CrudService<D, C, F, ID>,
                        protected readonly router: Router,
                        protected readonly rotaBase: string) {}

  protected tratarErro(error: unknown, mensagemPadrao: string): void {
    const resposta = error as HttpErrorResponse;
    this.erro.set(resposta.error?.detail ?? mensagemPadrao);
  }

  protected limparErro(): void {
    this.erro.set(null);
  }
}
