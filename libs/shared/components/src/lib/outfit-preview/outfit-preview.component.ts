import {Component, Input, OnChanges, SimpleChanges, ViewEncapsulation} from '@angular/core';
import {Outfit, Product, ProductCategory} from "../defs";


@Component({
  selector: 'outfit-planner-mf-outfit-preview',
  templateUrl: './outfit-preview.component.html',
  styleUrls: ['./outfit-preview.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class OutfitPreviewComponent implements OnChanges {
  // @ts-ignore
  @Input() outfit: Outfit;

  layout = {
    top: [ProductCategory.Jacket.valueOf(), ProductCategory.Sweatshirt.valueOf(), ProductCategory.Tshirt.valueOf()],
    middle: [ProductCategory.Trousers.valueOf()],
    bottom: [ProductCategory.Shoes.valueOf(), ProductCategory.Socks.valueOf()]
  }

  alignedProducts: Partial<Product>[][] = [];
  probability: number = 0;

  ngOnChanges(changes: SimpleChanges): void {
    this.alignedProducts = [];

    Object.values(this.layout).forEach((values) => {
      const products = this.outfit.products.filter((product) => values.includes(product.category))
      if (products.length === 0) {
        this.alignedProducts.push([{category: values[0]}])
      } else {
        this.alignedProducts.push(products);
      }
    })

    this.alignedProducts.forEach(row => {
      this.sortRow(row)
    })

  }


  private sortRow(row: Partial<Product>[]) {
    const category = row.find(el => el)?.category || '';
    Object.values(this.layout).forEach((values) => {
      if(values.includes(category)){
        row.sort((a, b) => {
          return values.indexOf(a.category || '') - values.indexOf(b.category || '')
        })
      }
    })
  }
}
