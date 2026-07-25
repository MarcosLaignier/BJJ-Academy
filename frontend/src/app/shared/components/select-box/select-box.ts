import { ChangeDetectionStrategy, Component, forwardRef, input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

export interface SelectOption {
  value: string;
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
export class SelectBox implements ControlValueAccessor {
  readonly label = input.required<string>();
  readonly options = input.required<SelectOption[]>();
  readonly emptyLabel = input('Todos');

  protected value = '';
  protected disabled = false;
  private onChange: (value: string) => void = () => undefined;
  private onTouched: () => void = () => undefined;

  writeValue(value: string | null): void {
    this.value = value ?? '';
  }

  registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(disabled: boolean): void {
    this.disabled = disabled;
  }

  protected update(event: Event): void {
    this.value = (event.target as HTMLSelectElement).value;
    this.onChange(this.value);
    this.onTouched();
  }
}
