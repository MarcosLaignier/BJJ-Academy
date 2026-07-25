import { ControlValueAccessor } from '@angular/forms';
import { Directive } from '@angular/core';

@Directive()
export abstract class BaseValueAccessor<T> implements ControlValueAccessor {
  protected value: T | null = null;
  protected disabled = false;

  private onChange: (value: T | null) => void = () => undefined;
  private onTouched: () => void = () => undefined;

  writeValue(value: T | null): void {
    this.value = value;
    this.afterWriteValue(value);
  }

  registerOnChange(fn: (value: T | null) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(disabled: boolean): void {
    this.disabled = disabled;
  }

  protected setValue(value: T | null): void {
    this.value = value;
    this.onChange(value);
  }

  protected touch(): void {
    this.onTouched();
  }

  protected afterWriteValue(_value: T | null): void {}
}
