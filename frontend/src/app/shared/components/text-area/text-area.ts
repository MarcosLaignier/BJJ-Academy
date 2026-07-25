import { ChangeDetectionStrategy, Component, forwardRef, input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

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
export class TextArea implements ControlValueAccessor {
  readonly label = input.required<string>();
  readonly placeholder = input('');
  readonly rows = input(3);

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
    this.value = (event.target as HTMLTextAreaElement).value;
    this.onChange(this.value);
  }

  protected touch(): void {
    this.onTouched();
  }
}
