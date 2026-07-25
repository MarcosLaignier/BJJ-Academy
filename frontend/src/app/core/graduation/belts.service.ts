import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CrudService } from '../../shared/core/crud/crud-service';
import { Faixa, FaixaFiltro, FaixaRequest } from './belt.models';

@Injectable({ providedIn: 'root' })
export class FaixasService implements CrudService<Faixa, FaixaRequest, FaixaFiltro> {
  private readonly http = inject(HttpClient);
  private readonly endpoint = 'http://localhost:8080/api/faixas';
  pesquisar(filtro: FaixaFiltro): Observable<Faixa[]> {
    let params = new HttpParams();
    if (filtro.nome) params = params.set('nome', filtro.nome);
    if (filtro.categoria) params = params.set('categoria', filtro.categoria);
    if (filtro.ativo !== null) params = params.set('ativo', filtro.ativo);
    return this.http.get<Faixa[]>(this.endpoint, { params });
  }
  buscarPorId(id: number): Observable<Faixa> { return this.http.get<Faixa>(`${this.endpoint}/${id}`); }
  atualizar(id: number, dto: FaixaRequest): Observable<Faixa> { return this.http.put<Faixa>(`${this.endpoint}/${id}`, dto); }
}
