import { ChangeDetectionStrategy, Component, computed, forwardRef, input } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseValueAccessor } from '../../core/forms/base-value-accessor';
import { SelectOption } from '../select-box/select-box';

type EnumValue = string | number;
type EnumObject = Readonly<Record<string, EnumValue>>;

@Component({
  selector: 'enum-select',
  templateUrl: './enum-select.html',
  styleUrl: './enum-select.scss',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => EnumSelect),
    multi: true,
  }],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EnumSelect extends BaseValueAccessor<EnumValue> {
  readonly label = input.required<string>();
  readonly enumObject = input.required<EnumObject>();
  readonly placeholder = input('Selecione');
  readonly labels = input<Readonly<Record<string, string>>>({});

  protected readonly options = computed<ReadonlyArray<SelectOption<EnumValue>>>(() =>
    Object.entries(this.enumObject())
      .filter(([key]) => Number.isNaN(Number(key)))
      .map(([key, value]) => ({
        value,
        label: this.labels()[key] ?? key,
      })));

  protected update(event: Event): void {
    const rawValue = (event.target as HTMLSelectElement).value;
    const option = this.options().find((item) => String(item.value) === rawValue);
    this.setValue(option?.value ?? null);
    this.touch();
  }
}
