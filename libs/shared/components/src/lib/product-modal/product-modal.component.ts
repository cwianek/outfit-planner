import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  HostListener, Inject,
  Input,
  Output,
  ViewChild
} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

interface ProductForm {
  category: FormControl<string | null>;
  image: FormControl<string | null>;
  fileName: FormControl<string | null>;
}

export interface ProductModalData{
  title: string,
  categories: string[],
}

export interface ProductAddedResult {
  category: string,
  image: string
}

@Component({
  selector: 'outfit-planner-mf-product-modal',
  templateUrl: './product-modal.component.html',
  styleUrls: ['./product-modal.component.scss'],
})
export class ProductModalComponent {
  @ViewChild('template') templateRef!: ElementRef;

  // @HostListener('click', ['$event.target']) onClick() {
  //   this.openModal();
  // }

  @Input() categories: String[] = [];

  @Input() title = ''

  @Output() productAdded: EventEmitter<ProductAddedResult> = new EventEmitter();


  productForm: FormGroup<ProductForm>;

  constructor(fb: FormBuilder, private dialogRef: MatDialogRef<ProductModalComponent>, private cd: ChangeDetectorRef, @Inject(MAT_DIALOG_DATA) public modalData: ProductModalData) {
    this.productForm = new FormGroup<ProductForm>({
      category: new FormControl(null, {validators: [Validators.required]}),
      image: new FormControl(null, {validators: [Validators.required]}),
      fileName: new FormControl(null, {validators: [Validators.required]}),
    })
  }

  onNoClick(){
    this.dialogRef.close();
  }

  openModal() {
    // const ref = this.modalService.open(this.templateRef, {size: 'lg'});
    // ref.result
    //   .then(() => {
    //     this.productAdded.emit(this.productForm.value as ProductAddedResult)
    //   })
    //   .finally(() => {
    //     this.productForm.reset();
    //   })
  }

  onFileChange(event: any) {
    let reader = new FileReader();

    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      this.productForm.patchValue({fileName: file.name})
      reader.readAsDataURL(file);

      reader.onload = () => {
        const base64 = reader.result as string;
        this.productForm.patchValue({
          image: base64.split(',')[1]
        });

        this.cd.markForCheck();
      };
    }
  }
}
