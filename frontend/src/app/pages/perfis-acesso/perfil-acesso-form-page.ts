import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import {
  PerfilAcesso,
  PerfilAcessoFiltro,
  PerfilAcessoRequest,
  Permissao,
} from '../../core/access/access.models';
import { PerfisAcessoService } from '../../core/access/perfis-acesso.service';
import { PageToolbar } from '../../shared/components/page-toolbar/page-toolbar';
import { PermissionSelector } from '../../shared/components/permission-selector/permission-selector';
import { TextArea } from '../../shared/components/text-area/text-area';
import { TextBox } from '../../shared/components/text-box/text-box';
import { SwitchBox } from '../../shared/components/switch-box/switch-box';
import { BaseFormCrud } from '../../shared/core/crud/base-form-crud';

@Component({
  selector: 'perfil-acesso-form-page',
  imports: [ReactiveFormsModule, PageToolbar, TextBox, TextArea, SwitchBox, PermissionSelector],
  templateUrl: './perfil-acesso-form-page.html',
  styleUrl: './perfil-acesso-form-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PerfilAcessoFormPage
  extends BaseFormCrud<
    PerfilAcesso,
    PerfilAcessoRequest,
    PerfilAcessoFiltro,
    number,
    Permissao[]
  > {
  private readonly fb = inject(FormBuilder);

  protected readonly permissoes = signal<Permissao[]>([]);
  protected readonly selecionadas = signal<Set<number>>(new Set());
  protected readonly formulario = this.fb.nonNullable.group({
    nome: ['', [Validators.required, Validators.maxLength(80)]],
    descricao: ['', Validators.maxLength(300)],
    ativo: [true],
  });

  constructor(private readonly perfilService: PerfisAcessoService,
              router: Router,
              route: ActivatedRoute) {
    super(perfilService, router, route, '/area/perfis', Number);
    this.inicializar();
  }

  protected override carregarDadosAuxiliares(): Observable<Permissao[]> {
    return this.perfilService.listarPermissoes();
  }

  protected override aposCarregar(perfil: PerfilAcesso | null, permissoes: Permissao[]): void {
    this.permissoes.set(permissoes);
    if (perfil) {
      this.preencher(perfil);
    }
  }

  protected salvar(): void {
    if (this.formulario.invalid || this.selecionadas().size === 0 || this.salvando()) {
      this.formulario.markAllAsTouched();
      if (this.selecionadas().size === 0) {
        this.erro.set('Selecione ao menos uma permissão.');
      }
      return;
    }

    const valor = this.formulario.getRawValue();
    const dto: PerfilAcessoRequest = {
      nome: valor.nome,
      descricao: valor.descricao || null,
      ativo: valor.ativo,
      permissoesIds: [...this.selecionadas()],
    };
    this.persistir(dto);
  }

  private preencher(perfil: PerfilAcesso): void {
    this.formulario.setValue({
      nome: perfil.nome,
      descricao: perfil.descricao ?? '',
      ativo: perfil.ativo,
    });
    this.selecionadas.set(new Set(perfil.permissoes.map((permissao) => permissao.id)));
  }
}
