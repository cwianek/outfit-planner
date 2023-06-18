import {Component, OnDestroy, OnInit} from '@angular/core';
import {
  Product,
  ProductCategory,
  ProductModalComponent,
  ProductsService
} from "@outfit-planner-mf/shared/components";
import {
  Subject,
  takeUntil} from "rxjs";
import {MatDialog} from '@angular/material/dialog';
import {ProductAddedResult, ProductModalData} from "@outfit-planner-mf/shared/components";

@Component({
  selector: 'outfit-planner-mf-product-list-container',
  templateUrl: './product-list-container.component.html',
  styleUrls: ['./product-list-container.component.scss'],
})
export class ProductListContainerComponent implements OnInit, OnDestroy {

  productCategories: string[] = [
    ProductCategory.Tshirt,
    ProductCategory.Jacket,
    ProductCategory.Sweatshirt,
    ProductCategory.Trousers,
    ProductCategory.Shoes,
    ProductCategory.Socks
  ]

  products: Product[] = [];

  componentDestroyed$: Subject<void> = new Subject<void>();

  constructor(private productsService: ProductsService,
              private dialog: MatDialog,
  ) {}

  openDialog(category: string): void {
    const modalData: ProductModalData = {
      title: 'Add new product',
      categories: this.productCategories,
      selectedCategory: category
    }
    const dialogRef = this.dialog.open(ProductModalComponent, {
      data: modalData,
      minWidth: '600px'
    });


    dialogRef.afterClosed().subscribe((result: ProductAddedResult | null) => {
      if (!result) {
        return;
      }
      this.productAdded(result);
    });
  }

  productAdded(product: ProductAddedResult) {
    this.productsService.addProduct(product).pipe(
      takeUntil(this.componentDestroyed$)
    ).subscribe(product => {
      this.products = [...this.products, product]
    });
  }

  ngOnInit(): void {
    this.productsService.getProducts().pipe(
      takeUntil(this.componentDestroyed$)
    ).subscribe(products => {
      this.products = [...products];
    })
  }

  ngOnDestroy(): void {
    this.componentDestroyed$.next();
  }

}
