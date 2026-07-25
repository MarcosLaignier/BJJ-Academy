export interface LoginRequest {
  email: string;
  senha: string;
}

export interface LoginResponse {
  token: string;
  tipo: 'Bearer';
  expiraEmSegundos: number;
  pessoaId: number;
  nome: string;
  perfil: string;
  permissoes: string[];
  trocaSenhaObrigatoria: boolean;
}

export interface Sessao {
  token: string;
  pessoaId: number;
  nome: string;
  perfil: string;
  permissoes: string[];
  trocaSenhaObrigatoria: boolean;
}
