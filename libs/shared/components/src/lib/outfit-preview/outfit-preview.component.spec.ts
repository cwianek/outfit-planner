import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OutfitPreviewComponent } from './outfit-preview.component';

describe('OutfitPreviewComponent', () => {
  let component: OutfitPreviewComponent;
  let fixture: ComponentFixture<OutfitPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OutfitPreviewComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(OutfitPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
