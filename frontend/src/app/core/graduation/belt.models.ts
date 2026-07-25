export const CATEGORIAS_FAIXA = { GERAL: 'GERAL', INFANTIL: 'INFANTIL', JUVENIL_ADULTO: 'JUVENIL_ADULTO', GRADUACAO_SUPERIOR: 'GRADUACAO_SUPERIOR' } as const;
export type CategoriaFaixa = keyof typeof CATEGORIAS_FAIXA;
export const ROTULOS_CATEGORIA_FAIXA: Readonly<Record<CategoriaFaixa, string>> = {
  GERAL: 'Geral', INFANTIL: 'Infantil', JUVENIL_ADULTO: 'Juvenil e adulto', GRADUACAO_SUPERIOR: 'Graduação superior',
};
export interface Faixa {
  id: number; codigo: string; nome: string; categoria: CategoriaFaixa; corPrincipalHex: string; corSecundariaHex: string | null;
  corTarjaHex: string; ordem: number; idadeMinima: number | null; quantidadeMaximaGraus: number; ativo: boolean;
}
export type FaixaRequest = Omit<Faixa, 'id' | 'codigo'>;
export interface FaixaFiltro { nome: string; categoria: CategoriaFaixa | null; ativo: boolean | null; }
