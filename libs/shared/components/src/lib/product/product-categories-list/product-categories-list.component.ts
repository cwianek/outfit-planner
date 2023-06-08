import {Component, EventEmitter, Input, OnInit, Output,} from '@angular/core';
import {ProductCategory} from "../../defs";
import {CategoriesOrientation} from "../defs";


@Component({
  selector: 'outfit-planner-mf-product-categories-list',
  templateUrl: './product-categories-list.component.html',
  styleUrls: ['./product-categories-list.component.scss'],
})
export class ProductCategoriesListComponent implements OnInit {

  @Input() orientation: CategoriesOrientation = CategoriesOrientation.Horizontal;

  @Output() categorySelected: EventEmitter<string> = new EventEmitter<string>();

  categoriesOrientation: typeof CategoriesOrientation = CategoriesOrientation;

  categories: string[] = [];

  selectedCategory: string = ''

  ngOnInit(): void {
    this.categories = Object.values(ProductCategory)
    this.onCategorySelected(this.categories[0])
  }

  onCategorySelected = (category: string) => {
    this.selectedCategory = category;
    this.categorySelected.emit(this.selectedCategory);
  }

}
