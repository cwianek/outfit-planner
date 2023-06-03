import {Component, EventEmitter, HostListener, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {
  BehaviorSubject, combineLatest,
  map,
  Observable,
} from "rxjs";
import {GroupedProducts, Product, ProductCategory} from "../defs";
import {UserService} from "@outfit-planner-mf/shared/auth";


@Component({
  selector: 'outfit-planner-mf-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss'],
})
export class ProductsListComponent implements OnChanges, OnInit {

  @HostListener('window:resize', [])
  onWindowResize() {
    this.screenWidth = window.innerWidth;
  }

  viewModel: Observable<{
    isLoggedIn: boolean,
    groupedProducts: GroupedProducts
  }> = new Observable();

  @Input() products: Product[] = [];

  @Output() productAdded: EventEmitter<string> = new EventEmitter<string>();

  screenWidth: number = 0;

  products$ = new BehaviorSubject<Product[] | null>(null);

  isLoggedIn: Observable<boolean> = this.userService.isUserLoggedIn$;

  groupedProducts$: Observable<GroupedProducts>;

  activeCategory: string = Object.values(ProductCategory)[0];

  constructor(private userService: UserService) {
    this.groupedProducts$ = this.products$.pipe(
      map((products) => products || []),
      map(this.groupProducts)
    )

    this.viewModel = combineLatest([this.groupedProducts$, this.isLoggedIn]).pipe(map(([groupedProducts, isLoggedIn]) => {
      return {isLoggedIn, groupedProducts }
    }))
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

  onProductAdded() {
    this.productAdded.emit(this.activeCategory);
  }

  ngOnInit(): void {
    this.onWindowResize()
  }
}
