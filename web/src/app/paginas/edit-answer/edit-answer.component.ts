import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnswerI } from 'src/app/models/answer-i';
import { QuestionService } from 'src/app/Service/question.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ServiceService } from 'src/app/Service/service.service';
import { DatePipe } from '@angular/common';
import { Rating } from 'primeng/rating';

@Component({
  selector: 'app-edit-answer',
  templateUrl: './edit-answer.component.html',
  styleUrls: ['./edit-answer.component.css'],
  providers: [MessageService],
})
export class EditAnswerComponent implements OnInit {
  public form: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(10)]],
    rating: ['', []],
  });
 
  @Input() item: any;
  constructor(
    private modalService: NgbModal,
    private services: QuestionService,
    private toastr: ToastrService,
    private route: Router,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    public authService: ServiceService
  ) {}

  answer: AnswerI = {
    id: '',
    userId: '',
    questionId: '',
    answer: '',
    position: 0,
    createAt: new Date(),
    updateAt: new Date()
  };

  oldAnswer: AnswerI = { ...this.answer };

  ngOnInit(): void {
  //this.answer=this.oldAnswer;

  }

  openVerticallyCentered(content: any) {
    this.modalService.open(content, { centered: true });
  }

  editAnswer(answerUpdate: AnswerI): void {
    this.services.editAnswer(answerUpdate).subscribe((v) => {});
    this.modalService.dismissAll();
    this.messageService.add({
      severity: 'success',
      summary: 'Se ha agregado la respuesta',
      
     });
     setTimeout(() => {
     window.location.reload();
   }, 2000);
  }        
}