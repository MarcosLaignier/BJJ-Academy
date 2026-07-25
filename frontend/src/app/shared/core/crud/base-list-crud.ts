import { Directive, signal } from '@angular/core';
import { Router } from '@angular/router';
import { finalize, forkJoin, Observable, of } from 'rxjs';
import { BaseCrud } from './base-crud';
import { CrudService } from './crud-service';

@Directive()
export abstract class BaseListCrud<D extends { id: ID }, C, F, ID = number, A = null>
  extends BaseCrud<D, C, F, ID> {

  protected readonly dataSource = signal<D[]>([]);

  protected constructor(service: CrudService<D, C, F, ID>,
                        router: Router,
                        rotaBase: string) {
    super(service, router, rotaBase);
  }

  protected abstract obterFiltro(): F;

  protected carregarDadosAuxiliares(): Observable<A> {
    return of(null as A);
  }

  protected aposCarregarDadosAuxiliares(_dados: A): void {}

  protected inicializar(): void {
    this.carregando.set(true);
    this.limparErro();
    forkJoin({
      registros: this.service.pesquisar(this.obterFiltro()),
      auxiliares: this.carregarDadosAuxiliares(),
    }).pipe(finalize(() => this.carregando.set(false))).subscribe({
      next: ({ registros, auxiliares }) => {
        this.dataSource.set(registros);
        this.aposCarregarDadosAuxiliares(auxiliares);
      },
      error: (error) => this.tratarErro(error, 'Não foi possível carregar os registros.'),
    });
  }

  protected pesquisar(): void {
    this.carregando.set(true);
    this.limparErro();
    this.service.pesquisar(this.obterFiltro())
      .pipe(finalize(() => this.carregando.set(false)))
      .subscribe({
        next: (registros) => this.dataSource.set(registros),
        error: (error) => this.tratarErro(error, 'Não foi possível carregar os registros.'),
      });
  }

  protected novo(): void {
    void this.router.navigateByUrl(`${this.rotaBase}/novo`);
  }

  protected editar(registro: D): void {
    void this.router.navigate([this.rotaBase, 'editar', registro.id]);
  }
}
