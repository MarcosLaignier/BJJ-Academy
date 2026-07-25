import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';

@Component({
  selector: 'page-toolbar',
  templateUrl: './page-toolbar.html',
  styleUrl: './page-toolbar.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PageToolbar {
  readonly title = input.required<string>();
  readonly subtitle = input<string>();
  readonly listMode = input(true);
  readonly showCreate = input(true);
  readonly saving = input(false);

  readonly create = output<void>();
  readonly filter = output<void>();
  readonly save = output<void>();
  readonly back = output<void>();
}
