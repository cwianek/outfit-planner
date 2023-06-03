import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {
} from "rxjs";
import {GroupedProducts, Product, ProductCategory} from "../defs";


@Component({
  selector: 'outfit-planner-mf-product-categories-list',
  templateUrl: './product-categories-list.component.html',
  styleUrls: ['./product-categories-list.component.scss'],
})
export class ProductCategoriesListComponent implements OnInit {

  categories: string[] = [];

  selectedCategory: string = ''

  @Input() orientation: 'horizontal' | 'vertical' = 'horizontal'

  @Output() categorySelected: EventEmitter<string> = new EventEmitter<string>();

  ngOnInit(): void {
    this.categories = Object.values(ProductCategory)
    this.onCategorySelected(this.categories[0])
  }

  onCategorySelected = (category: string) => {
    this.selectedCategory = category;
    this.categorySelected.emit(this.selectedCategory);
  }

}
