import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AngularFireModule } from '@angular/fire/compat';
import { RouterTestingModule } from '@angular/router/testing';
import { NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastrModule, ToastrService } from 'ngx-toastr';
import { MessageService } from 'primeng/api';
import { PrimeNGModule } from 'src/app/prime-ng/prime-ng.module';
import { QuestionService } from 'src/app/Service/question.service';
import { ServiceService } from 'src/app/Service/service.service';
import { environment } from 'src/environments/environment';

import { QuestionComponent } from './question.component';

describe('QuestionComponent', () => {
  let component: QuestionComponent;
  let fixture: ComponentFixture<QuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrimeNGModule, RouterTestingModule, HttpClientModule, ToastrModule.forRoot(), NgbModule, AngularFireModule.initializeApp(environment.firebaseConfig)],
      declarations: [ QuestionComponent ],
      providers: [MessageService, ServiceService, QuestionService, ToastrService, NgbModal]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
