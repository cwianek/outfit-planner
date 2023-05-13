import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {
  BehaviorSubject,
  map,
  Observable,
} from "rxjs";
import {Product} from "../defs";

type GroupedProducts = { [key: string]: Product[] };

@Component({
  selector: 'outfit-planner-mf-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss'],
})
export class ProductsListComponent implements OnChanges {

  @Input() products: Product[] = [];

  products$ = new BehaviorSubject<Product[] | null>(null);

  groupedProducts$: Observable<{ [key: string]: Product[] }>;

  constructor() {
    this.groupedProducts$ = this.products$.pipe(
      map((products) => products || []),
      map(this.groupProducts)
    )
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['products'] && changes['products']?.currentValue) {
      this.products$.next(changes['products'].currentValue);
    }
  }

  groupTrackBy(index: number, group: any): string {
    return group[0];
  }

  productTrackBy(index: number, product: Product): string {
    return product.id;
  }

  private groupProducts = (products: Product[]): GroupedProducts => {
    const grouped: GroupedProducts = {};
    products.forEach((product) => {
      grouped[product.category] = grouped[product.category] || [];
      grouped[product.category].push(product)
    })
    return grouped;
  }

}
