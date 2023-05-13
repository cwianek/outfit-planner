import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OutfitsListContainerComponent } from './outfits-list-container.component';

describe('OutfitsListContainerComponent', () => {
  let component: OutfitsListContainerComponent;
  let fixture: ComponentFixture<OutfitsListContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OutfitsListContainerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(OutfitsListContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
