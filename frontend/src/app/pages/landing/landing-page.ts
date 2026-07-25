import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

interface Benefit {
  icon: string;
  title: string;
  description: string;
}

interface TrainingGroup {
  age: string;
  name: string;
  description: string;
}

interface Schedule {
  time: string;
  days: string;
  group: string;
}

@Component({
  selector: 'landing-page',
  imports: [RouterLink],
  templateUrl: './landing-page.html',
  styleUrl: './landing-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LandingPage {
  protected readonly menuOpen = signal(false);
  protected readonly currentYear = new Date().getFullYear();

  protected readonly benefits: Benefit[] = [
    {
      icon: 'bi-shield-check',
      title: 'Ambiente seguro',
      description: 'Estrutura preparada e acompanhamento próximo em cada etapa do treino.',
    },
    {
      icon: 'bi-people',
      title: 'Comunidade forte',
      description: 'Respeito e parceria dentro e fora do tatame, independentemente do nível.',
    },
    {
      icon: 'bi-graph-up-arrow',
      title: 'Evolução constante',
      description: 'Metodologia progressiva para desenvolver técnica, confiança e disciplina.',
    },
  ];

  protected readonly groups: TrainingGroup[] = [
    { age: '04–07', name: 'Kids I', description: 'Coordenação, respeito e diversão.' },
    { age: '08–13', name: 'Kids II', description: 'Técnica, disciplina e autoconfiança.' },
    { age: '14+', name: 'Adulto', description: 'Fundamentos, avançado e competição.' },
  ];

  protected readonly schedules: Schedule[] = [
    { time: '07:00', days: 'Seg • Qua • Sex', group: 'Adulto — Todos os níveis' },
    { time: '17:30', days: 'Ter • Qui', group: 'Kids I — 4 a 7 anos' },
    { time: '18:30', days: 'Seg • Qua • Sex', group: 'Kids II — 8 a 13 anos' },
    { time: '20:00', days: 'Seg a Qui', group: 'Adulto — Todos os níveis' },
  ];

  protected toggleMenu(): void {
    this.menuOpen.update((open) => !open);
  }

  protected closeMenu(): void {
    this.menuOpen.set(false);
  }
}
