import {Component, Input, OnChanges, SimpleChanges, ViewEncapsulation} from '@angular/core';
import {Outfit, Product, ProductCategory} from "../defs";


@Component({
  selector: 'outfit-planner-mf-outfit-preview',
  templateUrl: './outfit-preview.component.html',
  styleUrls: ['./outfit-preview.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class OutfitPreviewComponent implements OnChanges{
  // @ts-ignore
  @Input() outfit: Outfit;

  layout = {
    top: [ProductCategory.Jacket.valueOf(), ProductCategory.Hoodie.valueOf(), ProductCategory.Tshirt.valueOf()],
    middle: [ProductCategory.Trousers.valueOf()],
    bottom: [ProductCategory.Shoe.valueOf(), ProductCategory.Socks.valueOf()]
  }

  alignedProducts: Partial<Product>[][] = [];
  probability: number = 0;

  ngOnChanges(changes: SimpleChanges): void {
    this.alignedProducts = [];

    Object.values(this.layout).forEach((values) => {
      const products = this.outfit.products.filter((product) => values.includes(product.category))
      if(products.length === 0){
        this.alignedProducts.push([{category: values[0]}])
      }else{
        this.alignedProducts.push(products);
      }
      this.probability = Math.round((this.outfit.matchProbability || 0) * 100)
    })
  }


}
