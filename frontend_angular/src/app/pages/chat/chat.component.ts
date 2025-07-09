import { ApiService } from './../../services/api.service';
import { Component, input } from '@angular/core';
import { SharedModule } from '../../utils/shared.component';
import {Message} from '../../models/message.response'

@Component({
  selector: 'app-chat',
  imports: [SharedModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent {

  constructor(private apiService:ApiService){}

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
    .getRandomeResponse(this.inputTextPrompt)
    .subscribe({
      next:(response)=>{
        console.log(response);
        this.inputTextPrompt='';
        this.messagesArray.push(new Message(response, false));
        this.loadingMessage=false;
      },
      error:(error)=>{
        console.log(error);
        this.loadingMessage=false;
      },
    });
  }
}
