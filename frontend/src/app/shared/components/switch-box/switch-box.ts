import { ChangeDetectionStrategy, Component, forwardRef, input } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseValueAccessor } from '../../core/forms/base-value-accessor';

@Component({
  selector: 'switch-box',
  templateUrl: './switch-box.html',
  styleUrl: './switch-box.scss',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => SwitchBox),
    multi: true,
  }],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SwitchBox extends BaseValueAccessor<boolean> {
  readonly label = input.required<string>();

  protected update(event: Event): void {
    this.setValue((event.target as HTMLInputElement).checked);
    this.touch();
  }
}
