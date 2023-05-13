import {AfterViewInit, Component, ElementRef, Input, ViewChild, ViewEncapsulation} from '@angular/core';
import {ProductsService} from "../services/products.service";

@Component({
  selector: 'outfit-planner-mf-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ProductItemComponent implements AfterViewInit {
  // @ts-ignore
  @Input() product: any;

  @ViewChild('imgRef') imgRef!: ElementRef;

  constructor(private productService: ProductsService) {}

  ngAfterViewInit(): void {
    if(!this.product.id){
      return;
    }

    this.productService.getImage(this.product.id).subscribe((img) => {
      const selectedFile = img;
      var reader = new FileReader();

      const imgRef = this.imgRef.nativeElement;
      imgRef.title = 'TITLE';

      reader.onload = (event) => {
        imgRef.src = event?.target?.result;
      }

      reader.readAsDataURL(selectedFile);

    })
  }


}
