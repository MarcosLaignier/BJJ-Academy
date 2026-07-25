import { Observable } from 'rxjs';

export interface CrudService<D, C, F, ID = number> {
  pesquisar(filtro: F): Observable<D[]>;
  buscarPorId(id: ID): Observable<D>;
  criar(dto: C): Observable<D>;
  atualizar(id: ID, dto: C): Observable<D>;
}
