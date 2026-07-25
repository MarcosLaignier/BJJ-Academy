import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { Permissao } from '../../../core/access/access.models';

@Component({
  selector: 'permission-selector',
  templateUrl: './permission-selector.html',
  styleUrl: './permission-selector.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PermissionSelector {
  readonly permissions = input.required<Permissao[]>();
  readonly selectedIds = input.required<Set<number>>();
  readonly selectedIdsChange = output<Set<number>>();

  protected toggle(id: number, checked: boolean): void {
    const updated = new Set(this.selectedIds());
    checked ? updated.add(id) : updated.delete(id);
    this.selectedIdsChange.emit(updated);
  }
}
