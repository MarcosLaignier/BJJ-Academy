import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { startWith } from 'rxjs';
import { CATEGORIAS_FAIXA, CategoriaFaixa, Faixa, FaixaFiltro, FaixaRequest, ROTULOS_CATEGORIA_FAIXA } from '../../core/graduation/belt.models';
import { FaixasService } from '../../core/graduation/belts.service';
import { BeltDisplay } from '../../shared/components/belt-display/belt-display';
import { EnumSelect } from '../../shared/components/enum-select/enum-select';
import { PageToolbar } from '../../shared/components/page-toolbar/page-toolbar';
import { SwitchBox } from '../../shared/components/switch-box/switch-box';
import { TextBox } from '../../shared/components/text-box/text-box';
import { BaseFormCrud } from '../../shared/core/crud/base-form-crud';

@Component({ selector: 'faixa-form-page', imports: [ReactiveFormsModule, PageToolbar, TextBox, EnumSelect, SwitchBox, BeltDisplay],
  templateUrl: './faixa-form-page.html', styleUrl: './faixa-form-page.scss', changeDetection: ChangeDetectionStrategy.OnPush })
export class FaixaFormPage extends BaseFormCrud<Faixa, FaixaRequest, FaixaFiltro> {
  private readonly fb = inject(FormBuilder);
  protected codigo = '';
  protected readonly categorias = CATEGORIAS_FAIXA;
  protected readonly rotulos = ROTULOS_CATEGORIA_FAIXA;
  protected readonly formulario = this.fb.nonNullable.group({
    nome: ['', Validators.required], categoria: this.fb.nonNullable.control<CategoriaFaixa>(CATEGORIAS_FAIXA.GERAL, Validators.required),
    corPrincipalHex: ['#F5F5F5', Validators.required], corSecundariaHex: [''], corTarjaHex: ['#111111', Validators.required],
    ordem: [1, [Validators.required, Validators.min(1)]], idadeMinima: [0, Validators.min(0)],
    quantidadeMaximaGraus: [4, [Validators.required, Validators.min(0), Validators.max(10)]], ativo: [true],
  });
  protected readonly preview = toSignal(this.formulario.valueChanges.pipe(startWith(this.formulario.getRawValue())),
    { initialValue: this.formulario.getRawValue() });
  constructor(service: FaixasService, router: Router, route: ActivatedRoute) { super(service, router, route, '/area/faixas', Number); this.inicializar(); }
  protected aposCarregar(faixa: Faixa | null): void {
    if (!faixa) return;
    this.codigo = faixa.codigo;
    this.formulario.setValue({ nome: faixa.nome, categoria: faixa.categoria, corPrincipalHex: faixa.corPrincipalHex,
      corSecundariaHex: faixa.corSecundariaHex ?? '', corTarjaHex: faixa.corTarjaHex, ordem: faixa.ordem,
      idadeMinima: faixa.idadeMinima ?? 0, quantidadeMaximaGraus: faixa.quantidadeMaximaGraus, ativo: faixa.ativo });
  }
  protected salvar(): void {
    if (this.formulario.invalid) { this.formulario.markAllAsTouched(); return; }
    const value = this.formulario.getRawValue();
    this.persistir({ ...value, corSecundariaHex: value.corSecundariaHex || null });
  }
}
