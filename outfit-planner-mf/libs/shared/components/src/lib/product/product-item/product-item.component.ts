import {
  AfterViewInit,
  Component,
  ElementRef,
  Input,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {ProductsService} from "../../services/products.service";

@Component({
  selector: 'outfit-planner-mf-product-item',
  templateUrl: './product-item.component.html',
  styleUrls: ['./product-item.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class ProductItemComponent implements AfterViewInit {
  @ViewChild('imgRef') imgRef!: ElementRef;

  @Input() product: any;

  constructor(private productService: ProductsService) {
  }

  ngAfterViewInit(): void {
    if (!this.product?.id) {
      return;
    }

    this.productService.getImage(this.product.id)
      .subscribe(this.readImage);
  }

  private readImage = (img: Blob) => {
    const selectedFile = img;
    const reader = new FileReader();

    const imgRef = this.imgRef.nativeElement;
    imgRef.title = '';

    reader.onload = (event) => {
      imgRef.src = event?.target?.result;
    }

    reader.readAsDataURL(selectedFile);
  }
}
