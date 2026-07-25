import { ChangeDetectionStrategy, Component, forwardRef, input } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseValueAccessor } from '../../core/forms/base-value-accessor';

@Component({
  selector: 'text-box',
  templateUrl: './text-box.html',
  styleUrl: './text-box.scss',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => TextBox),
    multi: true,
  }],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TextBox extends BaseValueAccessor<string> {
  readonly label = input.required<string>();
  readonly placeholder = input('');
  readonly type = input<'text' | 'email' | 'password' | 'search'>('text');

  protected update(event: Event): void {
    this.setValue((event.target as HTMLInputElement).value);
  }
}
