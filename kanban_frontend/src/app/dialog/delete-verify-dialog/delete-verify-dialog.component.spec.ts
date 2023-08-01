import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteVerifyDialogComponent } from './delete-verify-dialog.component';

describe('DeleteVerifyDialogComponent', () => {
  let component: DeleteVerifyDialogComponent;
  let fixture: ComponentFixture<DeleteVerifyDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteVerifyDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteVerifyDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
