import { ChangeDetectionStrategy, Component, forwardRef, input } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseValueAccessor } from '../../core/forms/base-value-accessor';

export interface SelectOption<T extends string | number = string> {
  value: T;
  label: string;
}

@Component({
  selector: 'select-box',
  templateUrl: './select-box.html',
  styleUrl: './select-box.scss',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => SelectBox),
    multi: true,
  }],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SelectBox<T extends string | number = string> extends BaseValueAccessor<T> {
  readonly label = input.required<string>();
  readonly options = input.required<ReadonlyArray<SelectOption<T>>>();
  readonly emptyLabel = input('Todos');

  protected update(event: Event): void {
    const rawValue = (event.target as HTMLSelectElement).value;
    const option = this.options().find((item) => String(item.value) === rawValue);
    this.setValue(option?.value ?? null);
    this.touch();
  }
}
