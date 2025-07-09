import { Component } from '@angular/core';
import { SharedModule } from '../../utils/shared.component';
import { ApiService } from '../../services/api.service';
import { Message } from './../../models/message.response';

@Component({
  selector: 'app-image',
  imports: [SharedModule],
  templateUrl: './image.component.html',
  styleUrl: './image.component.css'
})
export class ImageComponent {

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
      .getImageResponse(this.inputTextPrompt)
      .subscribe({
        next:(response)=>{
          console.log(response);
          this.inputTextPrompt='';
          this.pushInMessageArray(response,false,this.messagesArray);
          this.loadingMessage=false;
        },
        error:(error)=>{
          console.log(error);
          this.loadingMessage=false;
        },
      });
    }

    pushInMessageArray(msgArray:string[], query:boolean,messageArray:Message[]){
      for(const m of msgArray){
        messageArray.push(new Message(m, query));
      }
    }
}
