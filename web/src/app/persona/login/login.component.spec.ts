import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { MessageService } from 'primeng/api';
import { ServiceService } from 'src/app/Service/service.service';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { AngularFirestore } from '@angular/fire/compat/firestore';
import { AngularFireModule } from '@angular/fire/compat';
import { environment } from 'src/environments/environment';

describe('(1) TEST del componente "LoginComponent"', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[ReactiveFormsModule, RouterTestingModule, AngularFireModule.initializeApp(environment.firebaseConfig)],
      declarations: [ LoginComponent ],
      providers: [MessageService, ServiceService]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Debe retornar formulario invalido', () => {
    const form = component.form
    const email = component.form.controls['email']
    email.setValue('Lcastro0398@gmail.com')
    expect(form.invalid).toBeTrue
  })
});
