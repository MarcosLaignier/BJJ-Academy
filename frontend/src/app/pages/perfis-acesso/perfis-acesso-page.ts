import { ChangeDetectionStrategy, Component, computed, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import {
  PerfilAcesso,
  PerfilAcessoFiltro,
  PerfilAcessoRequest,
  Permissao,
} from '../../core/access/access.models';
import { PerfisAcessoService } from '../../core/access/perfis-acesso.service';
import { DataGrid, DataGridColumn } from '../../shared/components/data-grid/data-grid';
import { PageToolbar } from '../../shared/components/page-toolbar/page-toolbar';
import { SelectBox, SelectOption } from '../../shared/components/select-box/select-box';
import { BaseListCrud } from '../../shared/core/crud/base-list-crud';

@Component({
  selector: 'perfis-acesso-page',
  imports: [ReactiveFormsModule, PageToolbar, DataGrid, SelectBox],
  templateUrl: './perfis-acesso-page.html',
  styleUrl: './perfis-acesso-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PerfisAcessoPage
  extends BaseListCrud<
    PerfilAcesso,
    PerfilAcessoRequest,
    PerfilAcessoFiltro,
    number,
    Permissao[]
  > {
  protected readonly permissoes = signal<Permissao[]>([]);
  protected readonly filtro = new FormControl('', { nonNullable: true });
  protected readonly filtroStatus = new FormControl('', { nonNullable: true });
  protected readonly filtroPermissao = new FormControl('', { nonNullable: true });
  protected readonly opcoesStatus: SelectOption[] = [
    { value: 'true', label: 'Ativos' },
    { value: 'false', label: 'Inativos' },
  ];
  protected readonly opcoesPermissao = computed<SelectOption[]>(() =>
    this.permissoes().map((permissao) => ({
      value: String(permissao.id),
      label: permissao.nome,
    })));
  protected readonly colunas: DataGridColumn[] = [
    { field: 'nome', label: 'Nome' },
    { field: 'descricao', label: 'Descrição', value: (perfil: PerfilAcesso) => perfil.descricao ?? '—' },
    { field: 'permissoes', label: 'Permissões', value: (perfil: PerfilAcesso) => perfil.permissoes.length },
    { field: 'ativo', label: 'Status', value: (perfil: PerfilAcesso) => perfil.ativo ? 'Ativo' : 'Inativo' },
  ];

  constructor(private readonly perfilService: PerfisAcessoService,
              router: Router) {
    super(perfilService, router, '/area/perfis');
    this.inicializar();
  }

  protected obterFiltro(): PerfilAcessoFiltro {
    const ativo = this.filtroStatus.value === '' ? null : this.filtroStatus.value === 'true';
    const permissaoId = this.filtroPermissao.value === '' ? null : Number(this.filtroPermissao.value);
    return { nome: this.filtro.value, ativo, permissaoId };
  }

  protected override carregarDadosAuxiliares(): Observable<Permissao[]> {
    return this.perfilService.listarPermissoes();
  }

  protected override aposCarregarDadosAuxiliares(permissoes: Permissao[]): void {
    this.permissoes.set(permissoes);
  }

  protected limparFiltros(): void {
    this.filtro.setValue('');
    this.filtroStatus.setValue('');
    this.filtroPermissao.setValue('');
    this.pesquisar();
  }
}
