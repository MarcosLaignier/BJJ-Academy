import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  ElementRef,
  HostListener,
  inject,
  input,
  signal,
  forwardRef,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { NG_VALUE_ACCESSOR } from '@angular/forms';
import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  finalize,
  Observable,
  of,
  Subject,
  switchMap,
  tap,
} from 'rxjs';
import { BaseValueAccessor } from '../../core/forms/base-value-accessor';

@Component({
  selector: 'autocomplete-box',
  templateUrl: './autocomplete-box.html',
  styleUrl: './autocomplete-box.scss',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => AutocompleteBox),
    multi: true,
  }],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AutocompleteBox<T extends object> extends BaseValueAccessor<T> {
  readonly label = input.required<string>();
  readonly search = input.required<(term: string) => Observable<ReadonlyArray<T>>>();
  readonly displayWith = input.required<(item: T) => string>();
  readonly identify = input.required<(item: T) => string | number>();
  readonly placeholder = input('');
  readonly minimumLength = input(2);
  readonly emptyMessage = input('Nenhum resultado encontrado');

  protected readonly query = signal('');
  protected readonly results = signal<ReadonlyArray<T>>([]);
  protected readonly loading = signal(false);
  protected readonly open = signal(false);

  private readonly terms = new Subject<string>();
  private readonly destroyRef = inject(DestroyRef);
  private readonly element = inject(ElementRef<HTMLElement>);

  constructor() {
    super();

    this.terms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term) => {
        if (term.length < this.minimumLength()) {
          this.loading.set(false);
          return of<ReadonlyArray<T>>([]);
        }

        this.loading.set(true);
        return this.search()(term).pipe(
          catchError(() => of<ReadonlyArray<T>>([])),
          finalize(() => this.loading.set(false)),
        );
      }),
      tap((items) => {
        this.results.set(items);
        this.open.set(this.query().trim().length >= this.minimumLength());
      }),
      takeUntilDestroyed(this.destroyRef),
    ).subscribe();
  }

  @HostListener('document:click', ['$event'])
  protected closeFromOutside(event: Event): void {
    if (!this.element.nativeElement.contains(event.target as Node)) {
      this.open.set(false);
      this.touch();
    }
  }

  protected updateQuery(event: Event): void {
    const term = (event.target as HTMLInputElement).value;
    this.query.set(term);
    this.setValue(null);
    if (term.trim().length < this.minimumLength()) {
      this.results.set([]);
      this.open.set(false);
    }
    this.terms.next(term.trim());
  }

  protected showResults(): void {
    if (this.query().trim().length >= this.minimumLength()) {
      this.open.set(true);
    }
  }

  protected select(item: T): void {
    this.query.set(this.displayWith()(item));
    this.setValue(item);
    this.results.set([]);
    this.open.set(false);
    this.touch();
  }

  protected override afterWriteValue(value: T | null): void {
    this.query.set(value ? this.displayWith()(value) : '');
  }
}
