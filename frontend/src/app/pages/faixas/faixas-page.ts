import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CATEGORIAS_FAIXA, CategoriaFaixa, Faixa, FaixaFiltro, FaixaRequest, ROTULOS_CATEGORIA_FAIXA } from '../../core/graduation/belt.models';
import { FaixasService } from '../../core/graduation/belts.service';
import { DataGrid, DataGridColumn } from '../../shared/components/data-grid/data-grid';
import { EnumSelect } from '../../shared/components/enum-select/enum-select';
import { PageToolbar } from '../../shared/components/page-toolbar/page-toolbar';
import { SelectBox, SelectOption } from '../../shared/components/select-box/select-box';
import { BaseListCrud } from '../../shared/core/crud/base-list-crud';

@Component({ selector: 'faixas-page', imports: [ReactiveFormsModule, PageToolbar, DataGrid, SelectBox, EnumSelect],
  templateUrl: './faixas-page.html', styleUrl: './faixas-page.scss', changeDetection: ChangeDetectionStrategy.OnPush })
export class FaixasPage extends BaseListCrud<Faixa, FaixaRequest, FaixaFiltro> {
  protected readonly nome = new FormControl('', { nonNullable: true });
  protected readonly categoria = new FormControl<CategoriaFaixa | null>(null);
  protected readonly status = new FormControl('', { nonNullable: true });
  protected readonly categorias = CATEGORIAS_FAIXA;
  protected readonly rotulos = ROTULOS_CATEGORIA_FAIXA;
  protected readonly statusOptions: SelectOption[] = [{ value: 'true', label: 'Ativas' }, { value: 'false', label: 'Inativas' }];
  protected readonly colunas: DataGridColumn[] = [
    { field: 'ordem', label: 'Ordem' }, { field: 'nome', label: 'Faixa' },
    { field: 'categoria', label: 'Categoria', value: (faixa: Faixa) => ROTULOS_CATEGORIA_FAIXA[faixa.categoria] },
    { field: 'idadeMinima', label: 'Idade mínima', value: (faixa: Faixa) => faixa.idadeMinima ?? '—' },
    { field: 'quantidadeMaximaGraus', label: 'Graus' },
    { field: 'ativo', label: 'Status', value: (faixa: Faixa) => faixa.ativo ? 'Ativa' : 'Inativa' },
  ];
  constructor(service: FaixasService, router: Router) { super(service, router, '/area/faixas'); this.inicializar(); }
  protected obterFiltro(): FaixaFiltro {
    return { nome: this.nome.value, categoria: this.categoria.value, ativo: this.status.value === '' ? null : this.status.value === 'true' };
  }
  protected limpar(): void { this.nome.setValue(''); this.categoria.setValue(null); this.status.setValue(''); this.pesquisar(); }
}
