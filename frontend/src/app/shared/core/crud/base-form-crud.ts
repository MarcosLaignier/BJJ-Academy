import { Directive } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { finalize, forkJoin, Observable, of } from 'rxjs';
import { BaseCrud } from './base-crud';
import { CrudService } from './crud-service';

@Directive()
export abstract class BaseFormCrud<D, C, F, ID = number, A = null>
  extends BaseCrud<D, C, F, ID> {

  protected readonly id: ID | null;

  protected constructor(service: CrudService<D, C, F, ID>,
                        router: Router,
                        protected readonly route: ActivatedRoute,
                        rotaBase: string,
                        converterId: (value: string) => ID) {
    super(service, router, rotaBase);
    const idParam = route.snapshot.paramMap.get('id');
    this.id = idParam ? converterId(idParam) : null;
  }

  protected carregarDadosAuxiliares(): Observable<A> {
    return of(null as A);
  }

  protected abstract aposCarregar(registro: D | null, auxiliares: A): void;

  protected inicializar(): void {
    this.carregando.set(true);
    this.limparErro();
    forkJoin({
      registro: this.id !== null ? this.service.buscarPorId(this.id) : of(null),
      auxiliares: this.carregarDadosAuxiliares(),
    }).pipe(finalize(() => this.carregando.set(false))).subscribe({
      next: ({ registro, auxiliares }) => this.aposCarregar(registro, auxiliares),
      error: (error) => this.tratarErro(error, 'Não foi possível carregar o formulário.'),
    });
  }

  protected persistir(dto: C): void {
    if (this.salvando()) {
      return;
    }
    this.salvando.set(true);
    this.limparErro();
    const request$ = this.id !== null
      ? this.service.atualizar(this.id, dto)
      : this.service.criar(dto);

    request$.pipe(finalize(() => this.salvando.set(false))).subscribe({
      next: (registro) => this.aposSalvar(registro),
      error: (error) => this.tratarErro(error, 'Não foi possível salvar o registro.'),
    });
  }

  protected aposSalvar(_registro: D): void {
    this.voltar();
  }

  protected voltar(): void {
    void this.router.navigateByUrl(this.rotaBase);
  }
}
