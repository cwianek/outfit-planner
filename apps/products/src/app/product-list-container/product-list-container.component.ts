import {Component, OnDestroy, OnInit} from '@angular/core';
import {
  Product,
  ProductAddedResult, ProductCategory,
  ProductModalComponent,
  ProductModalData,
  ProductsService
} from "@outfit-planner-mf/shared/components";
import {Observable, Subscription} from "rxjs";
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'outfit-planner-mf-product-list-container',
  templateUrl: './product-list-container.component.html',
  styleUrls: ['./product-list-container.component.scss'],
})
export class ProductListContainerComponent implements OnInit, OnDestroy {

  productCategories: string[] = [ProductCategory.Tshirt, ProductCategory.Jacket, ProductCategory.Hoodie, ProductCategory.Trousers, ProductCategory.Shoe, ProductCategory.Socks]

  products: Product[] = [];

  genericSub: Subscription = new Subscription();

  constructor(private productsService: ProductsService, private dialog: MatDialog) {
  }

  openDialog(): void {
    const modalData: ProductModalData = {
      title: 'Add new product',
      categories: this.productCategories
    }
    const dialogRef = this.dialog.open(ProductModalComponent, {
      data: modalData,
      minWidth: '600px'
    });


    dialogRef.afterClosed().subscribe((result: ProductAddedResult | null) => {
      if(!result){
        return;
      }
      this.productAdded(result);
    });
  }

  productAdded(product: ProductAddedResult) {
    const sub = this.productsService.addProduct(product).subscribe(product => {
      this.products = [...this.products, product]
    });
    this.genericSub.add(sub);
  }

  ngOnInit(): void {
    const sub = this.productsService.getProducts().subscribe(products => {
      this.products = [...products];
    })
    this.genericSub.add(sub);
  }

  ngOnDestroy(): void {
    this.genericSub.unsubscribe();
  }

  fetchImage = (product: Product): Observable<any> => {
    return this.productsService.getImage(product.id);
  }

}
