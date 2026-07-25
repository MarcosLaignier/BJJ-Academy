import { ChangeDetectionStrategy, Component, forwardRef, input } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseValueAccessor } from '../../core/forms/base-value-accessor';

@Component({
  selector: 'text-area',
  templateUrl: './text-area.html',
  styleUrl: './text-area.scss',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => TextArea),
    multi: true,
  }],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TextArea extends BaseValueAccessor<string> {
  readonly label = input.required<string>();
  readonly placeholder = input('');
  readonly rows = input(3);

  protected update(event: Event): void {
    this.setValue((event.target as HTMLTextAreaElement).value);
  }
}
