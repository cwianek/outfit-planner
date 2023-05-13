import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateOutfitButtonComponent } from './create-outfit-button.component';

describe('CreateOutfitButtonComponent', () => {
  let component: CreateOutfitButtonComponent;
  let fixture: ComponentFixture<CreateOutfitButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateOutfitButtonComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CreateOutfitButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
