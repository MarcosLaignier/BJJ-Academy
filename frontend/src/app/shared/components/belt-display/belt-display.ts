import { ChangeDetectionStrategy, Component, computed, input } from '@angular/core';

@Component({ selector: 'belt-display', templateUrl: './belt-display.html', styleUrl: './belt-display.scss', changeDetection: ChangeDetectionStrategy.OnPush })
export class BeltDisplay {
  readonly name = input('Faixa');
  readonly primaryColor = input.required<string>();
  readonly secondaryColor = input<string | null>(null);
  readonly rankBarColor = input.required<string>();
  readonly degrees = input(0);
  readonly pattern = input<'CENTER_STRIPE' | 'ALTERNATING'>('CENTER_STRIPE');
  protected readonly background = computed(() => {
    const secondary = this.secondaryColor();
    if (!secondary) return this.primaryColor();
    return this.pattern() === 'CENTER_STRIPE'
      ? `linear-gradient(to bottom, ${this.primaryColor()} 0 38%, ${secondary} 38% 62%, ${this.primaryColor()} 62% 100%)`
      : `repeating-linear-gradient(to right, ${this.primaryColor()} 0 48px, ${secondary} 48px 96px)`;
  });
  protected readonly marks = computed(() => Array.from({ length: Math.max(0, this.degrees()) }));
}
