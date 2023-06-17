import {
  Component,
  EventEmitter,
  HostListener,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewEncapsulation
} from '@angular/core';
import {BehaviorSubject, combineLatest, map, Observable,} from "rxjs";
import {GroupedProducts, Product, ProductCategory, ScreenResolutions} from "../../defs";
import {UserService} from "@outfit-planner-mf/shared/auth";
import {CategoriesOrientation} from "../defs";
import {Message} from "primeng/api";


@Component({
  selector: 'outfit-planner-mf-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ProductsListComponent implements OnChanges, OnInit {

  @HostListener('window:resize', [])
  onWindowResize() {
    this.categoriesOrientation = window.innerWidth > ScreenResolutions.Large ? CategoriesOrientation.Horizontal : CategoriesOrientation.Vertical
  }

  viewModel: Observable<{
    isLoggedIn: boolean,
    groupedProducts: GroupedProducts
  }> = new Observable();

  @Input() products: Product[] = [];

  @Output() productAdded: EventEmitter<string> = new EventEmitter<string>();

  categoriesOrientation: CategoriesOrientation = CategoriesOrientation.Vertical;

  products$ = new BehaviorSubject<Product[] | null>(null);

  isLoggedIn: Observable<boolean> = this.userService.isUserLoggedIn$;

  groupedProducts$: Observable<GroupedProducts>;

  activeCategory: string = Object.values(ProductCategory)[0];

  messageText = 'These are sample clothes, log in to add your own.';

  constructor(private userService: UserService) {
    this.groupedProducts$ = this.products$.pipe(
      map((products) => products || []),
      map(this.groupProducts)
    )

    this.viewModel = combineLatest([this.groupedProducts$, this.isLoggedIn])
      .pipe(map(([groupedProducts, isLoggedIn]) => {
        return {isLoggedIn, groupedProducts}
      }))
  }

  ngOnInit(): void {
    this.onWindowResize()
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['products'] && changes['products']?.currentValue) {
      this.products$.next(changes['products'].currentValue);
    }
  }

  onProductAdded() {
    this.productAdded.emit(this.activeCategory);
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
