import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CrudService } from '../../shared/core/crud/crud-service';
import { PerfilAcesso, PerfilAcessoFiltro, PerfilAcessoRequest, Permissao } from './access.models';

@Injectable({ providedIn: 'root' })
export class PerfisAcessoService
  implements CrudService<PerfilAcesso, PerfilAcessoRequest, PerfilAcessoFiltro> {
  private readonly http = inject(HttpClient);
  private readonly api = 'http://localhost:8080/api';

  pesquisar(filtro: PerfilAcessoFiltro): Observable<PerfilAcesso[]> {
    let params = new HttpParams();
    if (filtro.nome) {
      params = params.set('nome', filtro.nome);
    }
    if (filtro.ativo !== null) {
      params = params.set('ativo', filtro.ativo);
    }
    if (filtro.permissaoId !== null) {
      params = params.set('permissaoId', filtro.permissaoId);
    }
    return this.http.get<PerfilAcesso[]>(`${this.api}/perfis-acesso`, { params });
  }

  listarPermissoes(): Observable<Permissao[]> {
    return this.http.get<Permissao[]>(`${this.api}/permissoes`);
  }

  buscarPorId(id: number): Observable<PerfilAcesso> {
    return this.http.get<PerfilAcesso>(`${this.api}/perfis-acesso/${id}`);
  }

  criar(dados: PerfilAcessoRequest): Observable<PerfilAcesso> {
    return this.http.post<PerfilAcesso>(`${this.api}/perfis-acesso`, dados);
  }

  atualizar(id: number, dados: PerfilAcessoRequest): Observable<PerfilAcesso> {
    return this.http.put<PerfilAcesso>(`${this.api}/perfis-acesso/${id}`, dados);
  }
}
