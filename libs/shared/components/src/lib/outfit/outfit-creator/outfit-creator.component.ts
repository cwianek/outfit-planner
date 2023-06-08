import {Component, Inject, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Outfit, Product} from "../../defs";
import {CategoriesOrientation} from "../../product/defs";

export interface OutfitCreatorModalData{
  products: Product[]
}

@Component({
  selector: 'outfit-planner-mf-outfit-creator',
  templateUrl: './outfit-creator.component.html',
  styleUrls: ['./outfit-creator.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class OutfitCreatorComponent {

  productByCategory: {[key: string] : Product[]} = {}

  selectedCategory: string = '';

  categories: string[] = [];

  outfit: Outfit = {
    id: '',
    products: []
  }

  categoriesOrientation: typeof CategoriesOrientation = CategoriesOrientation;

  constructor(@Inject(MAT_DIALOG_DATA) public modalData: OutfitCreatorModalData) {
    modalData.products.forEach((product) => {
      this.productByCategory[product.category] = this.productByCategory[product.category] || [];
      this.productByCategory[product.category].push(product);
    })
    this.categories = Object.keys(this.productByCategory);
    this.selectedCategory = Object.keys(this.productByCategory)[0];
  }

  addItem(product: Product) {
    this.outfit.products = this.outfit.products.filter((p) => p.category !== product.category);
    this.outfit = {
      ...this.outfit,
      products: [...this.outfit.products, product]
    }

  }
}
