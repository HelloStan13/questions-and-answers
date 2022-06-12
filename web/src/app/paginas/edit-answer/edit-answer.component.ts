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

  @Input() answer2: AnswerI[] | undefined;
  userLogged = this.authService.getUserLogged();
  answers: AnswerI[] | undefined;
  questions: AnswerI[] | undefined;
  @Input() idanswer: any='';  
  @Input() item: any;
  answer: AnswerI = {
    id:'',
    userId: '',
    questionId: '',
    answer: '',
    position: 0,
    createAt: new Date(),
    updateAt: new Date()
  };
  
  constructor(
    private modalService: NgbModal,
    private services: QuestionService,
    private toastr: ToastrService,
    private route: Router,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    public authService: ServiceService
  ) {}
  

  ngOnInit(): void {
    this.answer=this.item;
    this.getData();
  }
  getData(){    
    this.userLogged.subscribe(value=>{
    })    
  }
    
  openVerticallyCentered(content: any) {
    this.modalService.open(content, { centered: true });
  }

  editAnswer(answer: AnswerI): void{
    answer.id=this.answer.id;
    answer.userId = this.item.userId;
    answer.questionId = this.item.id;
    answer.updateAt=this.item.updateAt;

   this.services.editAnswer(answer).subscribe((v)=>{
    
   });

   this.modalService.dismissAll();
   this.messageService.add({
     severity: 'success',
     summary: 'Se ha actualizado la respuesta',          
    });
    /*
   setTimeout(() => {
     window.location.reload();
   }, 2000);*/
 }
}
