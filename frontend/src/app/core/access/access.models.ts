export interface Permissao {
  id: number;
  codigo: string;
  nome: string;
  descricao: string | null;
}

export interface PerfilAcesso {
  id: number;
  nome: string;
  descricao: string | null;
  ativo: boolean;
  permissoes: Permissao[];
}

export interface PerfilAcessoRequest {
  nome: string;
  descricao: string | null;
  ativo: boolean;
  permissoesIds: number[];
}

export interface PerfilAcessoFiltro {
  nome: string;
  ativo: boolean | null;
  permissaoId: number | null;
}
