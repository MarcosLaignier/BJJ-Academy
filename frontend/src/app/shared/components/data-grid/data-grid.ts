import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';

export interface DataGridColumn {
  field: string;
  label: string;
  value?: (row: any) => string | number;
}

@Component({
  selector: 'data-grid',
  templateUrl: './data-grid.html',
  styleUrl: './data-grid.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DataGrid {
  readonly rows = input.required<any[]>();
  readonly columns = input.required<DataGridColumn[]>();
  readonly loading = input(false);
  readonly emptyMessage = input('Nenhum registro encontrado.');
  readonly edit = output<any>();

  protected value(row: any, column: DataGridColumn): unknown {
    return column.value ? column.value(row) : row[column.field];
  }
}
