import { ChangeDetectionStrategy, Component, forwardRef, input } from '@angular/core';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import { BaseValueAccessor } from '../../core/forms/base-value-accessor';

type EntityId = string | number;

@Component({
  selector: 'entity-select',
  templateUrl: './entity-select.html',
  styleUrl: './entity-select.scss',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => EntitySelect),
    multi: true,
  }],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EntitySelect<T extends object> extends BaseValueAccessor<T> {
  readonly label = input.required<string>();
  readonly items = input.required<ReadonlyArray<T>>();
  readonly identify = input.required<(item: T) => EntityId>();
  readonly displayWith = input.required<(item: T) => string>();
  readonly placeholder = input('Selecione');

  protected selectedId(): string {
    return this.value ? String(this.identify()(this.value)) : '';
  }

  protected update(event: Event): void {
    const id = (event.target as HTMLSelectElement).value;
    const selected = this.items().find((item) => String(this.identify()(item)) === id);
    this.setValue(selected ?? null);
    this.touch();
  }
}
