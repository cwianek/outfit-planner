import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OutfitsListComponent } from './outfits-list.component';

describe('OutfitsListComponent', () => {
  let component: OutfitsListComponent;
  let fixture: ComponentFixture<OutfitsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OutfitsListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(OutfitsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
