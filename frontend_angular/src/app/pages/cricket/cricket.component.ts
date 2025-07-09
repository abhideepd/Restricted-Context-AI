import { Message } from './../../models/message.response';
import { Component } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { SharedModule } from '../../utils/shared.component';

@Component({
  selector: 'app-cricket',
  imports: [SharedModule],
  templateUrl: './cricket.component.html',
  styleUrl: './cricket.component.css'
})
export class CricketComponent {
  constructor(private apiService:ApiService){

  }
  inputTextPrompt:string='';
  messagesArray:Message[]=[];
  loadingMessage:boolean=false;


  askButtonClicked(){
      if(this.inputTextPrompt.trim()==''){
        return;
      }
      this.messagesArray.push(new Message(this.inputTextPrompt, true));
      this.loadingMessage=true;
      console.log("Ask button clicked");
      console.log(this.messagesArray);
      this.apiService
      .getCricketResponse(this.inputTextPrompt)
      .subscribe({
        next:(response)=>{
          console.log(response);
          this.inputTextPrompt='';
          this.messagesArray.push(new Message(response.content, false));
          this.loadingMessage=false;
        },
        error:(error)=>{
          console.log(error);
          this.loadingMessage=false;
        },
      });
    }
}
